package com.ls.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @className: UserLoginForm
 * @description:
 * @author: liusCoding
 * @create: 2020-02-15 16:03
 */

@Data
public class UserLoginForm {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
