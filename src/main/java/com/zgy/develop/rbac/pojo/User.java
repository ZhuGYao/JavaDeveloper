package com.zgy.develop.rbac.pojo;

import com.zgy.develop.annotation.db.Column;
import com.zgy.develop.annotation.db.Table;

import java.util.Date;

/**
 * @author zgy
 * @data 2021/5/9 18:51
 */

@Table("t_user")
public class User {

    private Long id;

    private String name;

    private String password;

    @Column("create_time")
    private Date createTime;

    @Column("update_time")
    private Date updateTime;

    private Integer deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
