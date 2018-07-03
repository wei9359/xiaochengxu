package com.yinhu.serviceimp;

import com.yinhu.tools.Pager;
import com.yinhu.mapper.BaseMapper;
import com.yinhu.service.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @ClassName BaseServiceImpl
 * @auther 魏星
 * @DATE 2018/6/20
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    private BaseMapper<T> baseMapper;

    public BaseServiceImpl() {
    }

    public void setMapper(BaseMapper<T> baseMapper) {
        this.baseMapper = baseMapper;
    }

    public void insert(T pojo) throws Exception {
        this.baseMapper.insert(pojo);
    }

    public List<T> queryList(Map<Object, Object> condition) throws Exception {
        return this.baseMapper.queryList(condition);
    }

    public Pager<T> queryPager(Map<Object, Object> condition, Integer beginRow, Integer pageSize, String orderBy, String sortBy, String isFuzzy) throws Exception {
        Pager<T> pager = new Pager();
        return pager.queryPage(this.baseMapper, condition, beginRow, pageSize, orderBy, sortBy, isFuzzy);
    }

    public T queryOne(Map<Object, Object> condition) throws Exception {
        return this.baseMapper.queryOne(condition);
    }

    public int count(Map<Object, Object> condition) throws Exception {
        return this.baseMapper.count(condition);
    }

    public T queryById(Object id) throws Exception {
        return this.baseMapper.queryById(id);
    }

    public void deleteById(Object id) throws Exception {
        this.baseMapper.deleteById(id);
    }

    public void deleteByCondition(Map<Object, Object> condition) throws Exception {
        this.baseMapper.deleteByCondition(condition);
    }

    public void update(T pojo) throws Exception {
        this.baseMapper.update(pojo);
    }

    public void updateNull(T pojo) throws Exception {
        this.baseMapper.updateNull(pojo);
    }
}
