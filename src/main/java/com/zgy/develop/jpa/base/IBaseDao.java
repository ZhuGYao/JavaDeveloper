package com.zgy.develop.jpa.common;

import java.util.List;

/**
 * @author zgy
 * @data 2021/4/16 11:11
 */

public interface IBaseDao<T> {

    Integer insert(T bean);

    Integer delete(T bean);

    Integer update(T bean);

    T selectOne(Integer id);

    List<T> selectAll();
}
