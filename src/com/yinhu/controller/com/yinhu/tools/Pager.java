package com.yinhu.controller.com.yinhu.tools;

import com.yinhu.mapper.BaseMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Page
 * @auther 魏星
 * @DATE 2018/6/20
 */
public class Pager<T> {
    private List<T> dataList = null;
    private int totalCount = 0;
    private int pageSize = 0;
    private int beginRow = 0;
    private int pageCount = 1;

    public Pager(List<T> dataList, Integer pageSize, Integer totalCount, Integer beginRow) {
        if (dataList != null) {
            this.dataList = dataList;
        }

        if (pageSize == null) {
            pageSize = 10;
        }

        this.setPageSize(pageSize);
        this.setTotalCount(totalCount);
        Double pageNum = Math.ceil(((double)this.getTotalCount() + 0.0D) / (double)pageSize);
        if (beginRow != null && beginRow >= 1 && totalCount != 0) {
            if (beginRow > pageNum.intValue()) {
                this.setBeginRow(pageNum.intValue());
            } else {
                this.setBeginRow(beginRow);
            }
        } else {
            this.setBeginRow(0);
        }

        this.setPageCount(pageNum.intValue());
    }

    public Pager(int totalCount, int pageSize, int beginRow, List<T> dataList) {
        this.dataList = dataList;
        this.pageSize = pageSize;
        this.beginRow = beginRow;
        this.totalCount = totalCount;
    }

    public Pager() {
    }

    public static Map<Object, Object> getCondition(Map<Object, Object> queryCondition, Integer beginRow, Integer pageSize, String orderBy, String sortBy) {
        if (queryCondition == null) {
            queryCondition = new HashMap();
        }

        if (beginRow != null && beginRow > 0) {
            beginRow = beginRow - 1;
            ((Map)queryCondition).put("beginRow", beginRow * pageSize);
        } else {
            beginRow = 0;
            ((Map)queryCondition).put("beginRow", 0);
        }

        if (pageSize != null) {
            ((Map)queryCondition).put("pageSize", pageSize);
        } else {
            ((Map)queryCondition).put("pageSize", "10");
        }

        if (orderBy != null) {
            ((Map)queryCondition).put("orderBy", orderBy);
        }

        if (sortBy != null) {
            ((Map)queryCondition).put("sortBy", sortBy);
        }

        return (Map)queryCondition;
    }

    public Pager<T> queryPage(BaseMapper mapper, Map<Object, Object> condition, Integer beginRow, Integer pageSize, String orderBy, String sortBy, String isFuzzy) throws Exception {
        if (isFuzzy != null && "1".equals(isFuzzy)) {
            condition.put("isFuzzy", isFuzzy);
        }

        int totalCount = mapper.count(condition);
        Map<Object, Object> queryCondition = getCondition(condition, beginRow, pageSize, orderBy, sortBy);
        List<T> lists = mapper.queryList(queryCondition);
        Pager<T> pager = new Pager(lists, pageSize, totalCount, beginRow);
        return pager;
    }

    public List<T> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getBeginRow() {
        return this.beginRow;
    }

    public void setBeginRow(int beginRow) {
        this.beginRow = beginRow;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
