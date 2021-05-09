package com.zgy.develop.rbac.dao;

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


}
