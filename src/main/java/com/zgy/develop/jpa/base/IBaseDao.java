package com.zgy.develop.jpa.base;

import java.util.List;
import java.util.Map;

/**
 * @author zgy
 * @data 2021/4/16 11:11
 */

public interface IBaseDao<T> {

    /**
     * 插入数据
     * @param bean
     * @return
     */
    Integer insert(T bean);

    /**
     * 删除数据
     * @param bean
     * @return
     */
    Integer delete(T bean);

    /**
     * 根据主键更新
     * @param bean
     * @return
     */
    Integer updatePrimaryKey(T bean);

    /**
     * 根据主键更新,忽略空值
     * @param bean
     * @return
     */
    Integer updatePrimaryKeySelective(T bean);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    T selectByPrimaryKey(Integer id);

    /**
     * 根据条件查询单条
     * @param customExample
     * @return
     */
    T selectOne(CustomExample customExample);

    /**
     * 查询全部
     * @return
     */
    List<T> selectAll();
}
