package com.ls.mall.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum ProductStatusEnum {

    ON_SALE(1, "在售"),

    OFF_SALE(2, "下架"),

    DELETE(3, "删除");

    private Integer code;
    private String desc;
}
