package com.ls.mall.service;

import com.github.pagehelper.PageInfo;
import com.ls.mall.form.ShippingForm;
import com.ls.mall.vo.ResponseVo;

import java.util.Map;

public interface IShippingService {

    /**
     * 添加地址
     *
     * @param uid  用户id
     * @param form 新增表单对象
     * @date: 2020/2/19
     * @return: com.ls.mall.vo.ResponseVo<java.lang.Integer>
     **/
    ResponseVo<Map<String, Integer>> add(Integer uid, ShippingForm form);


    /**
     * 删除地址
     *
     * @param uid        用户id
     * @param shippingId 地址id
     * @date: 2020/2/19
     * @return: com.ls.mall.vo.ResponseVo
     **/
    ResponseVo delete(Integer uid, Integer shippingId);

    /**
     * 更新地址
     *
     * @param uid        用户id
     * @param shippingId 地址id
     * @param form       表单更新对象
     * @date: 2020/2/19
     * @return: com.ls.mall.vo.ResponseVo
     **/
    ResponseVo update(Integer uid, Integer shippingId, ShippingForm form);

    /**
     * 获取地址列表
     *
     * @param uid      用户id
     * @param pageNum  当前页
     * @param pageSize 每页的数量
     * @date: 2020/2/19
     * @return: com.ls.mall.vo.ResponseVo<com.github.pagehelper.PageInfo>
     **/
    ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize);
}
