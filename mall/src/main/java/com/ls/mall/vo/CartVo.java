package com.ls.mall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @className: CatrVo
 * @description: 购物车VO
 * @author: liusCoding
 * @create: 2020-02-17 16:58
 */

@Data
public class CartVo {

    private List<CartProductVo> cartProductVoList;

    private Boolean selected;

    private BigDecimal cartTotalPrice;

    private Integer cartTotalQuantity;
}
