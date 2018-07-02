package com.yinhu.mapper;

import java.util.List;
import java.util.Map;

/**
 * @ClassName BaseMapper
 * @auther 魏星
 * @DATE 2018/6/20
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


public interface BaseMapper<T> {
    void insert(T var1) throws Exception;

    List<T> queryList(Map<Object, Object> var1) throws Exception;

    T queryOne(Map<Object, Object> var1) throws Exception;

    int count(Map<Object, Object> var1) throws Exception;

    T queryById(Object var1) throws Exception;

    void deleteById(Object var1) throws Exception;

    void deleteByCondition(Map<Object, Object> var1) throws Exception;

    void update(T var1) throws Exception;

    void updateNull(T var1) throws Exception;
}

