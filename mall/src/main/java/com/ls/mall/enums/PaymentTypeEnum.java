package com.ls.mall.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentTypeEnum {

    PAY_ONLINE(1, "在线支付");
    private Integer code;

    private String desc;
}
