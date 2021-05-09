package com.zgy.develop.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zgy
 * @data 2021/5/9 18:42
 */

public final class ThreadLocalUtil {

    private ThreadLocalUtil() {
    }

    /**
     * ThreadLocal的静态方法withInitial()会返回一个SuppliedThreadLocal对象
     * 而SuppliedThreadLocal<T> extends ThreadLocal<T>
     * 我们存进去的Map会作为的返回值：
     * protected T initialValue() {
     *    return supplier.get();
     * }
     *
     * 所以也相当于重写了initialValue()
     *
     */
    private final static ThreadLocal<Map<String, Object>> THREAD_CONTEXT = ThreadLocal.withInitial(
            () -> new HashMap<>(8)
    );

    /**
     * 根据key获取value
     * 比如key为USER_INFO，则返回"{'name':'bravo', 'age':18}"
     * {
     * ...THREAD_CONTEXT: {
     * ........."USER_INFO":"{'name':'bravo', 'age':18}",
     * ........."SCORE":"{'Math':99, 'English': 97}"
     * ...}
     * }
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        // getContextMap()表示要先获取THREAD_CONTEXT的value，也就是Map<String, Object>。然后再从Map<String, Object>中根据key获取
        return THREAD_CONTEXT.get().get(key);
    }

    /**
     * put操作，原理同上
     *
     * @param key
     * @param value
     */
    public static void put(String key, Object value) {
        THREAD_CONTEXT.get().put(key, value);
    }

    /**
     * 清除map里的某个值
     * 比如把
     * {
     * ...THREAD_CONTEXT: {
     * ........."USER_INFO":"{'name':'bravo', 'age':18}",
     * ........."SCORE":"{'Math':99, 'English': 97}"
     * ...}
     * }
     * 变成
     * {
     * ...THREAD_CONTEXT: {
     * ........."SCORE":"{'Math':99, 'English': 97}"
     * ...}
     * }
     *
     * @param key
     * @return
     */
    public static Object remove(String key) {
        return THREAD_CONTEXT.get().remove(key);
    }

    /**
     * 清除整个Map<String, Object>
     * 比如把
     * {
     * ...THREAD_CONTEXT: {
     * ........."USER_INFO":"{'name':'bravo', 'age':18}",
     * ........."SCORE":"{'Math':99, 'English': 97}"
     * ...}
     * }
     * 变成
     * {
     * ...THREAD_CONTEXT: {}
     * }
     */
    public static void clear() {
        THREAD_CONTEXT.get().clear();
    }

    /**
     * 从ThreadLocalMap中清除当前ThreadLocal存储的内容
     * 比如把
     * {
     * ...THREAD_CONTEXT: {
     * ........."USER_INFO":"{'name':'bravo', 'age':18}",
     * ........."SCORE":"{'Math':99, 'English': 97}"
     * ...}
     * }
     * 变成
     * {
     * }
     */
    public static void clearAll() {
        THREAD_CONTEXT.remove();
    }
}
