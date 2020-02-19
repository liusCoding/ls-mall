package com.ls.mall.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: CartUpdateForm
 * @description: 购物车更新表单
 * @author: liusCoding
 * @create: 2020-02-18 12:22
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartUpdateForm {

    private Integer quantity;

    private Boolean selected;
}
