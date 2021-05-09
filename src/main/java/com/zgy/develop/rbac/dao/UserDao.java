package com.zgy.develop.rbac.dao;

import com.zgy.develop.jpa.base.CustomExample;
import com.zgy.develop.jpa.base.MySQLBaseDao;
import com.zgy.develop.rbac.pojo.User;

/**
 * @author zgy
 * @data 2021/4/16 17:22
 */

public class UserDao extends MySQLBaseDao<User> {

    public int add(User user) {
        return this.insert(user);
    }

    public User selectOne(String name, String password) {

        CustomExample customExample = new CustomExample(User.class);
        CustomExample.CustomCriteria customCriteria = customExample.createCustomCriteria();
        customCriteria.andEqualTo("name", name);
        customCriteria.andEqualTo("password", password);

        this.selectOne(customExample);
        return null;
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        userDao.selectOne("123", "123");
    }
}
