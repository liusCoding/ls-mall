package com.ls.mall.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: Cart
 * @description: Redis存储购物车
 * @author: liusCoding
 * @create: 2020-02-17 18:46
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {

    private Integer productId;

    private Integer quantity;

    private Boolean productSelected;
}
