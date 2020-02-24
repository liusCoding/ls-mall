package com.ls.mall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @className: OrderCreateForm
 * @description: 订单创建表单
 * @author: liusCoding
 * @create: 2020-02-24 10:33
 */
@Data
public class OrderCreateForm {

    @NotNull
    private Integer shippingId;
}
