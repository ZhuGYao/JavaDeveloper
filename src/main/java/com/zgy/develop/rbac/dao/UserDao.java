package com.zgy.develop.rbac.dao;

import com.zgy.develop.jpa.base.CustomExample;
import com.zgy.develop.jpa.base.MySQLBaseDao;
import com.zgy.develop.rbac.pojo.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author zgy
 * @data 2021/4/16 17:22
 */

@Service
public class UserDao extends MySQLBaseDao<User> {

    public int add(User user) {
        return this.insert(user);
    }

    public User selectOne(String name, String password) {

        CustomExample customExample = new CustomExample(User.class);
        CustomExample.CustomCriteria customCriteria = customExample.createCustomCriteria();
        customCriteria.andEqualTo("name", name);
        customCriteria.andEqualTo("password", password);

        User user = this.selectOne(customExample);
        return user;
    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        User user = userDao.selectOne("123", "123");
        System.out.println(user);
//        String name = int.class.getSimpleName();
//        String name1 = Integer.class.getSimpleName();
//        System.out.println(name + " " + name1);
    }
}
