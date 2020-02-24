package com.ls.mall.form;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @className: CartAddForm
 * @description: 添加商品
 * @author: liusCoding
 * @create: 2020-02-17 17:07
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartAddForm {

    @NotNull
    private Integer productId;

    private Boolean selected = true;
}
