package com.zgy.develop.rbac.dao;

import com.zgy.develop.jpa.base.CustomExample;
import com.zgy.develop.jpa.base.MySQLBaseDao;
import com.zgy.develop.rbac.pojo.Role;
import com.zgy.develop.rbac.pojo.UserRoleMerge;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zgy
 * @data 2021/5/10 19:56
 */

@Component
public class UserRoleMergeDao extends MySQLBaseDao<UserRoleMerge> {

    public List<UserRoleMerge> selectListByUserId(Long userId) {

        CustomExample customExample = new CustomExample(UserRoleMerge.class);
        CustomExample.CustomCriteria customCriteria = customExample.createCustomCriteria();
        customCriteria.andEqualTo("userId", userId);
        List<UserRoleMerge> userRoleMerges = this.selectList(customExample);

        return userRoleMerges;
    }
}
