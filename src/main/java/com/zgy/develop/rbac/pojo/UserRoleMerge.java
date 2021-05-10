package com.zgy.develop.rbac.pojo;

import com.zgy.develop.annotation.db.Column;
import com.zgy.develop.annotation.db.Table;

/**
 * @author zgy
 * @data 2021/5/10 19:00
 */

@Table("t_user_role")
public class UserRoleMerge {

    private Long id;

    @Column("role_id")
    private Long roleId;

    @Column("permission_id")
    private Long permissionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}
