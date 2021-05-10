package com.zgy.develop.rbac.dao;

import com.zgy.develop.jpa.base.CustomExample;
import com.zgy.develop.jpa.base.MySQLBaseDao;
import com.zgy.develop.rbac.pojo.RolePermissionMerge;
import com.zgy.develop.rbac.pojo.UserRoleMerge;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zgy
 * @data 2021/5/10 19:55
 */

@Component
public class RolePermissionMergeDao extends MySQLBaseDao<RolePermissionMerge> {

    public List<RolePermissionMerge> selectListByRoleIds(List<Long> roleIds) {

        List<RolePermissionMerge> list = new ArrayList<>();
        for (Long roleId : roleIds) {
            CustomExample customExample = new CustomExample(RolePermissionMerge.class);
            CustomExample.CustomCriteria customCriteria = customExample.createCustomCriteria();
            customCriteria.andEqualTo("roleId", roleId);
            List<RolePermissionMerge> rolePermissionMerges = this.selectList(customExample);
            list.addAll(rolePermissionMerges);
        }
        return list;
    }
}
