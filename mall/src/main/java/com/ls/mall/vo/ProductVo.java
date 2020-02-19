package com.ls.mall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @className: ProductVo
 * @description:
 * @author: liusCoding
 * @create: 2020-02-16 19:31
 */

@Data
public class ProductVo {

    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private BigDecimal price;

    private Integer status;
}
