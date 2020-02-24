package com.ls.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ls.mall.dao.OrderItemMapper;
import com.ls.mall.dao.OrderMapper;
import com.ls.mall.dao.ProductMapper;
import com.ls.mall.dao.ShippingMapper;
import com.ls.mall.enums.ProductStatusEnum;
import com.ls.mall.enums.ResponseEnum;
import com.ls.mall.pojo.*;
import com.ls.mall.service.ICartService;
import com.ls.mall.service.IOrderService;
import com.ls.mall.vo.OrderItemVo;
import com.ls.mall.vo.OrderVo;
import com.ls.mall.vo.ResponseVo;
import com.ls.mall.vo.ShippingVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.ls.mall.enums.OrderStatusEnum.*;
import static com.ls.mall.enums.PaymentTypeEnum.PAY_ONLINE;
import static com.ls.mall.enums.ResponseEnum.ORDER_NO_EXIST;
import static com.ls.mall.enums.ResponseEnum.ORDER_STATUS_ERROR;

/**
 * @className: OrderServiceImpl
 * @description: 订单service
 * @author: liusCoding
 * @create: 2020-02-19 15:39
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ICartService cartService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
        //1.收货地址校验（总之要查来的）
        Shipping shipping = shippingMapper.selectByIdAndUid(shippingId, uid);
        if (Objects.isNull(shipping)) {
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }

        //2.获取购物车，校验（是否有商品，库存）
        List<Cart> cartList = cartService.listForCart(uid).stream()
                .filter(Cart::getProductSelected)
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(cartList)) {
            return ResponseVo.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }
        //获取购物车的商品，再找数据库查找
        List<Integer> productIds = cartList.stream().map(Cart::getProductId).collect(Collectors.toList());

        List<Product> products = productMapper.selectByIds(productIds);
        Map<Integer, Product> productMap = products.stream().collect(Collectors.toMap(Product::getId, Function.identity(), (k1, k2) -> k1));

        List<OrderItem> orderItemList = new ArrayList<>();
        Long orderNo = generateOrderNo();
        for (Cart cart : cartList) {
            //是否有商品
            Product product = productMap.get(cart.getProductId());
            if (Objects.isNull(product)) {
                return ResponseVo.error(ResponseEnum.PRODUCT_NO_EXIST, "商品不存在.productId:" + cart.getProductId());
            }
            //商品上下架状态
            if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
            }
            //库存是否充足
            if (product.getStock() < cart.getQuantity()) {
                return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR, "库存不正确." + product.getName());
            }


            OrderItem orderItem = buildOrderItem(uid, orderNo, product, cart.getQuantity());

            orderItemList.add(orderItem);


            //减库存
            product.setStock(product.getStock() - cart.getQuantity());
            int rowForProduct = productMapper.updateByPrimaryKey(product);
            if (rowForProduct <= 0) {
                return ResponseVo.error(ResponseEnum.ERROR);
            }

        }
        Order order = buildOrder(uid, orderNo, shippingId, orderItemList);
        //3.计算总价，只计算选中的商品

        //4.生成订单，入库：order和order_item
        int rowForOrder = orderMapper.insertSelective(order);
        if (rowForOrder <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        int rowForOrderItem = orderItemMapper.batchInsert(orderItemList);
        if (rowForOrderItem <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }


        //更新购物车（选中的商品） (实际就是删除购物车)
        cartList.forEach(
                cart -> cartService.delete(uid, cart.getProductId())
        );
        //构造orderVo

        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
        return ResponseVo.success(orderVo);
    }

    /**
     * 查询订单列表
     *
     * @param uid      用户id
     * @param pageNum  起始页
     * @param pageSize 每页的数量
     * @date: 2020/2/23
     * @return: com.ls.mall.vo.ResponseVo<com.github.pagehelper.PageInfo>
     **/
    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectByUid(uid);

        Set<Long> orderNoSet = orderList.stream().map(Order::getOrderNo).collect(Collectors.toSet());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);
        Map<Long, List<OrderItem>> orderItemMap = orderItemList.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderNo));
        Set<Integer> shippingIdSet = orderList.stream().map(Order::getShippingId).collect(Collectors.toSet());

        List<Shipping> shippingList = shippingMapper.selectByIdSet(shippingIdSet);
        Map<Integer, Shipping> shippingMap = shippingList.stream().collect(Collectors.toMap(Shipping::getId, Function.identity()));
//        for (Order order : orderList) {
//            buildOrderVo(order,orderItemMap.get(order.getOrderNo()),shippingMap.get(order.getShippingId()));
//        }
        //换成stream方式
        List<OrderVo> orderVoList = orderList.stream().map(order -> {
            OrderVo orderVo = buildOrderVo(order,
                    orderItemMap.get(order.getOrderNo()),
                    shippingMap.get(order.getShippingId()));
            return orderVo;
        }).collect(Collectors.toList());

        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVoList);
        return ResponseVo.success(pageInfo);
    }

    /**
     * 查询订单详情
     *
     * @param uid     用户id
     * @param orderNo 订单号
     * @date: 2020/2/23
     * @return: com.ls.mall.vo.ResponseVo<com.ls.mall.vo.OrderVo>
     **/
    @Override
    public ResponseVo<OrderVo> detail(Integer uid, Long orderNo) {

        Order order = orderMapper.selectByOrderNo(orderNo);
        if (Objects.isNull(order) || uid.equals(order.getId())) {
            return ResponseVo.error(ORDER_NO_EXIST);
        }
        Set<Long> orderNoSet = new HashSet<>();
        orderNoSet.add(order.getOrderNo());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());

        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
        return ResponseVo.success(orderVo);
    }

    /**
     * 取消订单
     *
     * @param uid     用户id
     * @param orderNo 订单号
     * @date: 2020/2/24
     * @return: com.ls.mall.vo.ResponseVo
     **/
    @Override
    public ResponseVo cancel(Integer uid, Long orderNo) {
        //校验该订单属不属于该用户
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (Objects.isNull(order) || uid.equals(order.getId())) {
            return ResponseVo.error(ORDER_NO_EXIST);
        }

        //只有【未付款】订单可以取消，看自己公司业务
        if (!NO_PAY.getCode().equals(order.getStatus())) {
            return ResponseVo.error(ORDER_STATUS_ERROR);
        }

        //修改订单状态
        order.setStatus(CANCELED.getCode());
        order.setCloseTime(LocalDateTime.now());

        int row = orderMapper.updateByPrimaryKeySelective(order);
        if (row <= 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        return ResponseVo.success();
    }

    /**
     * 修改订单状态
     *
     * @param orderNo 订单号
     * @date: 2020/2/24
     * @return: void
     **/
    @Override
    public void paid(Long orderNo) {
        //校验该订单属不属于该用户
        Order order = orderMapper.selectByOrderNo(orderNo);
        if (Objects.isNull(order)) {
            throw new RuntimeException(ORDER_NO_EXIST.getMsg() + "订单号" + orderNo);
        }

        //只有【未付款】订单可以变成已付款，看自己公司业务
        if (!NO_PAY.getCode().equals(order.getStatus())) {
            throw new RuntimeException(ORDER_STATUS_ERROR.getMsg() + "订单号" + orderNo);
        }

        //修改订单状态
        order.setStatus(PAID.getCode());
        order.setCloseTime(LocalDateTime.now());

        int row = orderMapper.updateByPrimaryKeySelective(order);
        if (row <= 0) {
            throw new RuntimeException("将订单更新为已支付状态失败" + "订单号" + orderNo);
        }

    }


    private OrderVo buildOrderVo(Order order, List<OrderItem> orderItemList, Shipping shipping) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);

        ShippingVo shippingVo = new ShippingVo();
        BeanUtils.copyProperties(shipping, shippingVo);
        orderVo.setShippingVo(shippingVo);
        List<OrderItemVo> orderItemVoList = orderItemList.stream().map(orderItem -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(orderItem, orderItemVo);
            return orderItemVo;
        }).collect(Collectors.toList());

        orderVo.setOrderItemVos(orderItemVoList);
        if (Objects.isNull(shipping)) {
            orderVo.setShippingVo(shippingVo);
            orderVo.setShippingId(shipping.getId());
        }
        return orderVo;
    }

    private Order buildOrder(Integer uid, Long orderNo,
                             Integer shippingId,
                             List<OrderItem> orderItemList) {
        BigDecimal totalPrice = orderItemList.stream().map(OrderItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        return Order.builder()
                .orderNo(orderNo)
                .paymentType(PAY_ONLINE.getCode())
                .postage(0)
                .status(NO_PAY.getCode())
                .userId(uid)
                .shippingId(shippingId)
                .payment(totalPrice)
                .build();
    }

    private Long generateOrderNo() {
        return System.currentTimeMillis() + new Random().nextInt();
    }

    private OrderItem buildOrderItem(Integer uid, Long orderNo, Product product, Integer quantity) {
        OrderItem orderItem = OrderItem.builder()
                .userId(uid)
                .currentUnitPrice(product.getPrice())
                .productId(product.getId())
                .productImage(product.getMainImage())
                .productName(product.getName())
                .quantity(quantity)
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .orderNo(orderNo)
                .build();
        return orderItem;
    }
}
