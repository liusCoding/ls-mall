package com.ls.mall.service;

import com.github.pagehelper.PageInfo;
import com.ls.mall.vo.OrderVo;
import com.ls.mall.vo.ResponseVo;

public interface IOrderService {

    /**
     * 创建订单
     *
     * @param uid        用户id
     * @param shippingId 地址id
     * @date: 2020/2/19
     * @return: com.ls.mall.vo.ResponseVo<com.ls.mall.vo.OrderVo>
     **/
    ResponseVo<OrderVo> create(Integer uid, Integer shippingId);


    /**
     * 查询订单列表
     *
     * @param uid      用户id
     * @param pageNum  起始页
     * @param pageSize 每页的数量
     * @date: 2020/2/23
     * @return: com.ls.mall.vo.ResponseVo<com.github.pagehelper.PageInfo>
     **/
    ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);

    /**
     * 查询订单详情
     *
     * @param uid     用户id
     * @param orderNo 订单号
     * @date: 2020/2/23
     * @return: com.ls.mall.vo.ResponseVo<com.ls.mall.vo.OrderVo>
     **/
    ResponseVo<OrderVo> detail(Integer uid, Long orderNo);


    /**
     * 取消订单
     *
     * @param uid     用户id
     * @param orderNo 订单号
     * @date: 2020/2/24
     * @return: com.ls.mall.vo.ResponseVo
     **/
    ResponseVo cancel(Integer uid, Long orderNo);

    /**
     * 修改订单状态
     *
     * @param orderNo 订单号
     * @date: 2020/2/24
     * @return: void
     **/
    void paid(Long orderNo);

}
