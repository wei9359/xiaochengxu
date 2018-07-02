package com.yinhu.service;

import com.yinhu.controller.com.yinhu.tools.Pager;

import java.util.List;
import java.util.Map;

/**
 * @ClassName BaseService
 * @auther 魏星
 * @DATE 2018/6/20
 */
public interface BaseService<T> {
    void insert(T var1) throws Exception;

    List<T> queryList(Map<Object, Object> var1) throws Exception;

    Pager<T> queryPager(Map<Object, Object> var1, Integer var2, Integer var3, String var4, String var5, String var6) throws Exception;

    T queryOne(Map<Object, Object> var1) throws Exception;

    int count(Map<Object, Object> var1) throws Exception;

    T queryById(Object var1) throws Exception;

    void deleteById(Object var1) throws Exception;

    void deleteByCondition(Map<Object, Object> var1) throws Exception;

    void update(T var1) throws Exception;

    void updateNull(T var1) throws Exception;
}

