package com.zgy.develop.rbac.dao;

import com.zgy.develop.jpa.base.MySQLBaseDao;
import com.zgy.develop.rbac.pojo.Permission;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zgy
 * @data 2021/5/10 19:54
 */

@Component
public class PermissionDao extends MySQLBaseDao<Permission> {

    public List<Permission> selectByIds(List<Long> ids) {

        List<Permission> list = new ArrayList<>();
        for (Long id : ids) {
            Permission permission = selectByPrimaryKey(id);
            if (permission != null) {
                list.add(permission);
            }
        }
        return list;
    }
}
