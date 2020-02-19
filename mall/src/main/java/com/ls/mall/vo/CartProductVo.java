package com.ls.mall.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @className: CartProductVo
 * @description:
 * @author: liusCoding
 * @create: 2020-02-17 17:00
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartProductVo {

    private Integer productId;

    /**
     * 购买的数量
     */
    private Integer quantity;

    private String productName;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private Integer productStatus;

    /**
     * 商品数量 * 单价
     */
    private BigDecimal productTotalPrice;

    private Integer productStock;

    /**
     * 商品是否选中
     */
    private Boolean productSelected;
}
