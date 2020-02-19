package com.ls.mall.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {

    ADMIN(0, "管理员"),
    CUSTOMER(1, "普通用户");
    private Integer code;
    private String msg;
}
