package com.ls.mall.service.impl;

import com.ls.mall.dao.UserMapper;
import com.ls.mall.enums.RoleEnum;
import com.ls.mall.pojo.User;
import com.ls.mall.service.IUserService;
import com.ls.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.ls.mall.enums.ResponseEnum.*;

/**
 * @className: UserServiceImpl
 * @description:
 * @author: liusCoding
 * @create: 2020-02-14 16:03
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     *
     * @param user
     * @date: 2020/2/14
     * @return: void
     **/
    @Override
    public ResponseVo register(User user) {


        //写入数据库
        //校验  username不能重复  email不能重复
        int usernameCount = userMapper.countByUsername(user.getUsername());
        if (usernameCount > 0) {
            return ResponseVo.error(USERNAME_EXIST);
        }

        // email不能重复
        int emailCount = userMapper.countByEmail(user.getEmail());
        if (emailCount > 0) {
            return ResponseVo.error(EMAIL_EXIST);
        }

        //MD5加密（Spring自带）
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(password);
        user.setRole(RoleEnum.CUSTOMER.getCode());

        //写入数据库
        int result = userMapper.insertSelective(user);
        if (result == 0) {
            return ResponseVo.error(ERROR);
        }
        return ResponseVo.success();
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @date: 2020/2/15
     * @return: com.ls.mall.vo.ResponseVo<com.ls.mall.pojo.User>
     **/
    @Override
    public ResponseVo<User> login(String username, String password) {

        User user = userMapper.selectByUsername(username);

        if (Objects.isNull(user)) {
            //用户名不存在 (返回：用户名或密码错误）
            return ResponseVo.error(USERNAME_OR_PASSWORD_ERROR);
        }

        if (!user.getPassword().equalsIgnoreCase(
                DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
            //密码错误  (返回：用户名或密码错误）
            return ResponseVo.error(USERNAME_OR_PASSWORD_ERROR);
        }

        user.setPassword(null);
        return ResponseVo.success(user);
    }
}
