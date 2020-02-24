package com.ls.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.ls.mall.dao.ProductMapper;
import com.ls.mall.enums.ProductStatusEnum;
import com.ls.mall.enums.ResponseEnum;
import com.ls.mall.form.CartAddForm;
import com.ls.mall.form.CartUpdateForm;
import com.ls.mall.pojo.Cart;
import com.ls.mall.pojo.Product;
import com.ls.mall.service.ICartService;
import com.ls.mall.vo.CartProductVo;
import com.ls.mall.vo.CartVo;
import com.ls.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

import static com.ls.mall.enums.ResponseEnum.CART_PRODUCT_NO_EXIST;

/**
 * @className: CartServiceImpl
 * @description: 购物车service
 * @author: liusCoding
 * @create: 2020-02-17 18:02
 */
@Service
public class CartServiceImpl implements ICartService {

    private final static String CART_REDIS_KEY = "cart_%d";

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 添加商品
     *
     * @param cartAddForm
     * @date: 2020/2/17
     * @return: javax.xml.ws.Response<com.ls.mall.vo.CartVo>
     **/
    @Override
    public ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm) {

        Integer quantity = 1;
        Product product = productMapper.selectByPrimaryKey(cartAddForm.getProductId());
        //1.商品是否存在
        if (Objects.isNull(product)) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }


        //2.商品是否正常在售

        if (!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())) {
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }

        //3.商品库存是否充足

        if (product.getStock() <= 0) {
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }

        //4.写入到redis  导入依赖  yml配置redis
//        redisTemplate.opsForValue()
//                .set(String.format(CART_REDIS_KEY,uid),
//                        JSON.toJSONString(
//                                Cart.builder().productId(product.getId()).quantity(quantity).productSelected(cartAddForm.getSelected()).build())
//                );


        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY, uid);
        String value = opsForHash.get(redisKey, String.valueOf(product.getId()));
        Cart cart;
        if (StringUtils.isEmpty(value)) {
            //没有该商品
            cart = Cart.builder().productId(product.getId()).quantity(quantity).productSelected(cartAddForm.getSelected()).build();
        } else {
            //已经有了  数量+1
            cart = JSON.parseObject(value, Cart.class);
            cart.setQuantity(cart.getQuantity() + quantity);
        }

        opsForHash.put(redisKey, String.valueOf(product.getId()),
                JSON.toJSONString(cart));
        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> list(Integer uid) {

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);

        Set<Map.Entry<String, String>> entrySet = entries.entrySet();

        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entrySet) {
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cart = JSON.parseObject(entry.getValue(), Cart.class);

            //TODO 需要优化，使用mysql里的in

            Product product = productMapper.selectByPrimaryKey(productId);
            if (Objects.nonNull(product)) {
                CartProductVo cartProductVo = CartProductVo.builder()
                        .productId(productId)
                        .quantity(cart.getQuantity())
                        .productMainImage(product.getMainImage())
                        .productName(product.getName())
                        .productPrice(product.getPrice())
                        .productSelected(cart.getProductSelected())
                        .productStock(product.getStock())
                        .productSubtitle(product.getSubtitle())
                        .productTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())))
                        .build();

                cartProductVoList.add(cartProductVo);

            }
        }

        cartVo.setSelected(cartProductVoList.stream().allMatch(cartProductVo -> cartProductVo.getProductSelected().equals(true)));
        cartVo.setCartProductVoList(cartProductVoList);
        //只计算选中的价格
        cartVo.setCartTotalPrice(cartProductVoList.stream().filter(e -> e.getProductSelected().equals(true)).map(CartProductVo::getProductTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        cartVo.setCartTotalQuantity(cartProductVoList.stream().map(CartProductVo::getQuantity).reduce(0, Integer::sum));
        return ResponseVo.success(cartVo);
    }

    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm updateForm) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();

        String redisKey = String.format(CART_REDIS_KEY, uid);
        String value = opsForHash.get(redisKey, String.valueOf(productId));

        if (StringUtils.isEmpty(value)) {
            return ResponseVo.error(CART_PRODUCT_NO_EXIST);
        }
        Cart cart = JSON.parseObject(value, Cart.class);
        if (Objects.nonNull(updateForm.getQuantity()) && updateForm.getQuantity() >= 0) {
            cart.setQuantity(updateForm.getQuantity());
        }
        if (Objects.nonNull(updateForm.getSelected())) {
            cart.setProductSelected(updateForm.getSelected());
        }

        opsForHash.put(redisKey, String.valueOf(productId), JSON.toJSONString(cart));

        return list(uid);
    }

    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY, uid);
        String value = opsForHash.get(redisKey, String.valueOf(productId));
        if (StringUtils.isEmpty(value)) {
            //没有该商品报错
            return ResponseVo.error(CART_PRODUCT_NO_EXIST);
        }

        opsForHash.delete(redisKey, String.valueOf(productId));

        return list(uid);

    }

    /**
     * 全选中
     *
     * @param uid
     * @date: 2020/2/18
     * @return: javax.xml.ws.Response<com.ls.mall.vo.CartVo>
     **/
    @Override
    public ResponseVo<CartVo> selectAll(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY, uid);

        List<Cart> cartList = listForCart(uid);

        for (Cart cart : cartList) {
            cart.setProductSelected(true);
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()), JSON.toJSONString(cart));
        }

        return list(uid);
    }

    /**
     * 全不选中
     *
     * @param uid
     * @date: 2020/2/18
     * @return: javax.xml.ws.Response<com.ls.mall.vo.CartVo>
     **/
    @Override
    public ResponseVo<CartVo> unSelectAll(Integer uid) {

        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY, uid);

        List<Cart> cartList = listForCart(uid);

        for (Cart cart : cartList) {
            cart.setProductSelected(false);
            opsForHash.put(redisKey, String.valueOf(cart.getProductId()), JSON.toJSONString(cart));
        }

        return list(uid);
    }

    /**
     * 购物车商品数量总和
     *
     * @param uid
     * @date: 2020/2/18
     * @return: javax.xml.ws.Response<java.lang.Integer>
     **/
    @Override
    public ResponseVo<Integer> sum(Integer uid) {

        List<Cart> cartList = listForCart(uid);
        Integer sum = cartList.stream().map(Cart::getQuantity).reduce(0, Integer::sum);
        return ResponseVo.success(sum);
    }

    @Override
    public List<Cart> listForCart(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY, uid);
        Map<String, String> entries = opsForHash.entries(redisKey);
        Set<Map.Entry<String, String>> entrySet = entries.entrySet();

        List<Cart> cartList = new ArrayList<>();
        for (Map.Entry<String, String> entry : entrySet) {
            cartList.add(JSON.parseObject(entry.getValue(), Cart.class));
        }

        return cartList;
    }
}
