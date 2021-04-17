package com.zgy.develop.jpa.test;

import com.zgy.develop.annotation.db.Column;
import com.zgy.develop.annotation.db.Table;

/**
 * @author zgy
 * @data 2021/4/16 17:22
 */

@Table("t_user")
public class User {

    @Column("name")
    private String name;

    @Column("age")
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
