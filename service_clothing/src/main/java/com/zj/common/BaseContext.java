package com.zj.common;

/**
 * 基于ThreadLocal封装的工具类，用于保存和获取当前线程的数据
 */
public class BaseContext {
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

    public static void setCurrentId(Integer id) {
        threadLocal.set(id);
    }

    public static Integer getCurrentId() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
