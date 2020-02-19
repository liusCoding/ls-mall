package com.ls.mall.service;

import com.ls.mall.pojo.User;
import com.ls.mall.vo.ResponseVo;

public interface IUserService {


    /**
     * 用户注册
     *
     * @param user
     * @date: 2020/2/14
     * @return: void
     **/
    ResponseVo<User> register(User user);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @date: 2020/2/15
     * @return: com.ls.mall.vo.ResponseVo<com.ls.mall.pojo.User>
     **/
    ResponseVo<User> login(String username, String password);
}
