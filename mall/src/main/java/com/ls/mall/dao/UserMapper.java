package com.ls.mall.dao;

import com.ls.mall.pojo.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int countByUsername(String username);

    /**
     * 查询邮箱是否存在
     *
     * @param email 邮箱
     * @date: 2020/2/15
     * @return: int
     **/
    int countByEmail(String email);

    /**
     * 查找用户（通过用户名）
     *
     * @param username 用户名
     * @date: 2020/2/15
     * @return: com.ls.mall.pojo.User
     **/
    User selectByUsername(String username);
}