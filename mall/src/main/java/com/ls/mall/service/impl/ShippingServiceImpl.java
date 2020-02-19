package com.ls.mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.ls.mall.dao.ShippingMapper;
import com.ls.mall.enums.ResponseEnum;
import com.ls.mall.form.ShippingForm;
import com.ls.mall.pojo.Shipping;
import com.ls.mall.service.IShippingService;
import com.ls.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: ShippingServiceImpl
 * @description: 地址Service实现
 * @author: liusCoding
 * @create: 2020-02-19 10:18
 */
@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    /**
     * 添加地址
     *
     * @param uid  用户id
     * @param form 新增表单对象
     * @date: 2020/2/19
     * @return: com.ls.mall.vo.ResponseVo<java.lang.Integer>
     **/
    @Override
    public ResponseVo<Map<String, Integer>> add(Integer uid, ShippingForm form) {

        Shipping shipping = new Shipping();
        BeanUtils.copyProperties(form, shipping);
        shipping.setUserId(uid);
        int row = shippingMapper.insertSelective(shipping);
        if (row == 0) {
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        HashMap<String, Integer> map = new HashMap<>(16);
        map.put("shippingId", shipping.getId());
        return ResponseVo.success(map);
    }

    /**
     * 删除地址
     *
     * @param uid        用户id
     * @param shippingId 地址id
     * @date: 2020/2/19
     * @return: com.ls.mall.vo.ResponseVo
     **/
    @Override
    public ResponseVo delete(Integer uid, Integer shippingId) {

        int row = shippingMapper.deleteByIdAndUid(uid, shippingId);
        if (row == 0) {
            return ResponseVo.error(ResponseEnum.DELETE_SHIPPING_FAIL);
        }
        return ResponseVo.success();
    }

    /**
     * 更新地址
     *
     * @param uid        用户id
     * @param shippingId 地址id
     * @param form       表单更新对象
     * @date: 2020/2/19
     * @return: com.ls.mall.vo.ResponseVo
     **/
    @Override
    public ResponseVo update(Integer uid, Integer shippingId, ShippingForm form) {
        return null;
    }

    /**
     * 获取地址列表
     *
     * @param uid      用户id
     * @param pageNum  当前页
     * @param pageSize 每页的数量
     * @date: 2020/2/19
     * @return: com.ls.mall.vo.ResponseVo<com.github.pagehelper.PageInfo>
     **/
    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        return null;
    }
}
