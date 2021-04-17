package com.zgy.develop.jpa.test;

import com.zgy.develop.jpa.base.MySQLBaseDao;

/**
 * @author zgy
 * @data 2021/4/16 17:22
 */

public class UserDao extends MySQLBaseDao<User> {

    public int add(User user) {
        return this.insert(user);
    }

    public static void main(String[] args) {
        User user = new User();
        user.setName("ccc");
//        user.setAge(18);
        UserDao userDao = new UserDao();
        userDao.add(user);
    }
}
