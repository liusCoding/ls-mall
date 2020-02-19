package com.ls.mall.service.impl;

import com.ls.mall.enums.ResponseEnum;
import com.ls.mall.enums.RoleEnum;
import com.ls.mall.pojo.User;
import com.ls.mall.service.IUserService;
import com.ls.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional
public class UserServiceImplTest {

    @Autowired
    private IUserService userService;


    public static final String USERNAME = "admin";
    public static final String PASSWORD = "admin";

    @Test
    public void register() {

        User user = User.builder()
                .username("jerry")
                .password("123456")
                .email("144333434@qq.com")
                .role(RoleEnum.ADMIN.getCode())
                .build();

        userService.register(user);
    }

    @Test
    public void login() {

        ResponseVo<User> userResponseVo = userService.login(USERNAME, PASSWORD);

        log.info("【userResponseVo】:{}", userResponseVo.getData());

        Integer status = userResponseVo.getStatus();
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), status);
    }

}