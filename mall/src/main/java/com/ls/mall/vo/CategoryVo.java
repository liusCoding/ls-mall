package com.ls.mall.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: CategoryVo
 * @description: 分类VO
 * @author: liusCoding
 * @create: 2020-02-16 17:37
 */

@Data
public class CategoryVo {
    private Integer id;

    /**
     * 父类别id当id=0时说明是根节点,一级类别
     */
    private Integer parentId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 类别状态1-正常,2-已废弃
     */
    private Integer status;

    /**
     * 排序编号,同类展示顺序,数值相等则自然排序
     */
    private Integer sortOrder;

    private List<CategoryVo> subCategories = new ArrayList<>(10);
}
