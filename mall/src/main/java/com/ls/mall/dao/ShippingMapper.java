package com.ls.mall.dao;

import com.ls.mall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int deleteByIdAndUid(@Param("shippingId") Integer shippingId, @Param("uid") Integer uid);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);

    List<Shipping> selectByUid(Integer uid);

    Shipping selectByIdAndUid(@Param("shippingId") Integer shippingId, @Param("uid") Integer uid);


    List<Shipping> selectByIdSet(@Param("idSet") Set<Integer> idSet);

}