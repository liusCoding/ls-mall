package com.ls.mall.controller;

import com.ls.mall.constants.MallConst;
import com.ls.mall.form.UserLoginForm;
import com.ls.mall.form.UserRegisterForm;
import com.ls.mall.pojo.User;
import com.ls.mall.service.IUserService;
import com.ls.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @className: UserController
 * @description:
 * @author: liusCoding
 * @create: 2020-02-14 17:12
 */
@RestController
@RequestMapping
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/user/register")
    public ResponseVo register(@Valid @RequestBody UserRegisterForm userForm) {
        log.info("【user】={}", userForm);
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        ResponseVo responseVo = userService.register(user);
        return responseVo;

    }


    @PostMapping("/user/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm user,
                                  HttpSession httpSession) {


        ResponseVo<User> userResponseVo = userService.login(user.getUsername(), user.getPassword());

        //设置Session
        httpSession.setAttribute(MallConst.CURRENT_USER, userResponseVo.getData());
        return userResponseVo;
    }

    @GetMapping("/user")
    public ResponseVo<User> userInfo(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);

        return ResponseVo.success(user);
    }

    @PostMapping("/user/logout")
    public ResponseVo logout(HttpSession session) {
        log.info("/user/logout sessionId = {}", session.getId());
        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success();
    }
}
