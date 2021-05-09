package com.zgy.develop.jpa.base;

import com.zgy.develop.annotation.db.Column;
import com.zgy.develop.annotation.db.Table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 仿tk,乞丐版
 * @author zgy
 * @data 2021/5/10 1:09
 */

public class CustomExample {

    // 实体Class
    private Class<?> entityClass;

    // 属性与列名映射
    private Map<String, String> propertyMap;

    // 查询条件
    private CustomCriteria criteria;

    // 表名
    private String tableName;

    public CustomExample(Class<?> entityClass) {
        this.entityClass = entityClass;
        propertyMap = new HashMap<>();
        initTableAndProperty(entityClass);
    }

    /**
     * 初始化表名以及属性
     * @param entityClass
     */
    private void initTableAndProperty(Class<?> entityClass) {

        // 1.获取表名
        tableName = entityClass.getName();
        Table table = entityClass.getAnnotation(Table.class);
        if (table != null && !"".equals(table.value())) {
            tableName = table.value();
        }

        // 2.获取属性
        Field[] fields = entityClass.getDeclaredFields();
        // 3.遍历属性,进行设置
        for (Field f : fields) {
            String propertyName = f.getName();
            Column column = f.getAnnotation(Column.class);
            if (column != null && !"".equals(column.value())) {
                propertyName = column.value();
            }
            propertyMap.put(f.getName(), propertyName);
        }

    }

    /**
     * 创建
     * @return
     */
    public CustomCriteria createCustomCriteria() {
        CustomCriteria customCriteria = new CustomCriteria(propertyMap);
        this.criteria = customCriteria;
        return customCriteria;
    }

    public CustomCriteria getCriteria() {
        return criteria;
    }

    public String getTableName() {
        return tableName;
    }

    /**
     * 条件集
     */
    public static class CustomCriteria {

        private Map<String, String> propertyMap;

        private List<CustomCriterion> criteria;

        public CustomCriteria(Map<String, String> propertyMap) {
            this.propertyMap = propertyMap;
            criteria = new ArrayList<>();
        }

        public CustomCriteria andEqualTo(String property, Object value) {
            addCustomCriterion(column(property) + " =", value);
            return this;
        }

        /**
         * 添加查询条件
         * @param condition
         * @param value
         */
        private void addCustomCriterion(String condition, Object value) {
            if (value != null) {
                criteria.add(new CustomCriterion(condition, value));
            }
        }

        /**
         * 获取列名
         * @param property
         * @return
         */
        private String column(String property) {
            // 如果不存在直接抛出异常
            if (!propertyMap.containsKey(property)) {
                throw new RuntimeException(property + "属性不存在");
            }
            return propertyMap.get(property);
        }

        public List<CustomCriterion> getCriteria() {
            return criteria;
        }
    }


    /**
     * 条件
     */
    public static class CustomCriterion {

        private String condition;

        private Object value;

        public CustomCriterion(String condition, Object value) {
            this.condition = condition;
            this.value = value;
        }

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }
    }
}
