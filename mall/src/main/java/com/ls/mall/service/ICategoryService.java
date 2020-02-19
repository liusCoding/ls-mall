package com.ls.mall.service;

import com.ls.mall.vo.CategoryVo;
import com.ls.mall.vo.ResponseVo;

import java.util.List;
import java.util.Set;

public interface ICategoryService {

    /**
     * 查询所有分类
     *
     * @param
     * @date: 2020/2/16
     * @return: com.ls.mall.vo.ResponseVo<java.util.List < com.ls.mall.vo.CategoryVo>>
     **/
    ResponseVo<List<CategoryVo>> selectAll();

    /**
     * 查找子类
     *
     * @param id
     * @param resultSet
     * @date: 2020/2/16
     * @return: void
     **/
    void findSubCategoryId(Integer id, Set<Integer> resultSet);
}
