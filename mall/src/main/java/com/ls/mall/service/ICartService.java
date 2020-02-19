package com.ls.mall.service;

import com.ls.mall.form.CartAddForm;
import com.ls.mall.form.CartUpdateForm;
import com.ls.mall.vo.CartVo;
import com.ls.mall.vo.ResponseVo;

public interface ICartService {

    /**
     * 添加商品
     *
     * @param cartAddForm
     * @param uid
     * @date: 2020/2/17
     * @return: javax.xml.ws.Response<com.ls.mall.vo.CartVo>
     **/
    ResponseVo<CartVo> add(Integer uid, CartAddForm cartAddForm);

    /**
     * 获取购物车列表
     *
     * @param uid
     * @date: 2020/2/18
     * @return: com.ls.mall.vo.ResponseVo<com.ls.mall.vo.CartVo>
     **/
    ResponseVo<CartVo> list(Integer uid);

    /**
     * 更新购物车
     *
     * @param uid
     * @param productId
     * @param updateForm
     * @date: 2020/2/18
     * @return: com.ls.mall.vo.ResponseVo<com.ls.mall.vo.CartVo>
     **/
    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm updateForm);

    /**
     * 删除购物侧
     *
     * @param uid
     * @param productId
     * @date: 2020/2/18
     * @return: com.ls.mall.vo.ResponseVo<com.ls.mall.vo.CartVo>
     **/
    ResponseVo<CartVo> delete(Integer uid, Integer productId);

    /**
     * 全选中
     *
     * @param uid
     * @date: 2020/2/18
     * @return: javax.xml.ws.Response<com.ls.mall.vo.CartVo>
     **/
    ResponseVo<CartVo> selectAll(Integer uid);

    /**
     * 全不选中
     *
     * @param uid
     * @date: 2020/2/18
     * @return: javax.xml.ws.Response<com.ls.mall.vo.CartVo>
     **/
    ResponseVo<CartVo> unSelectAll(Integer uid);

    /**
     * 购物车商品数量总和
     *
     * @param uid
     * @date: 2020/2/18
     * @return: javax.xml.ws.Response<java.lang.Integer>
     **/
    ResponseVo<Integer> sum(Integer uid);

}
