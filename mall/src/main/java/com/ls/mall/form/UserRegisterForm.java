package com.ls.mall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @className: UserForm
 * @description:
 * @author: liusCoding
 * @create: 2020-02-14 19:17
 */

@Data
public class UserRegisterForm {

    @NotBlank//用于字符串 判断空格
    //@NotNull 非null校验
    //@NotEmpty 用于集合
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

}
