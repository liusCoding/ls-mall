package com.ls.mall.dao;

import com.ls.mall.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Category  操作数据库接口
 * @author: liusCoding
 * @date: 2019/11/21
 **/

/** @Mapper   mybatis doo层注解*/
public interface CategoryMapper {

     /**
     * 通过id查找类别
     * @author: liusCoding
     * @date: 2019/11/21
     * @param id
     * @return: com.ls.mall.pojo.Category
     **/

    @Select("select * from mall_category where id = ${id}")
    Category findById(@Param("id") Integer id);

    Category queryById(Integer id);
}
