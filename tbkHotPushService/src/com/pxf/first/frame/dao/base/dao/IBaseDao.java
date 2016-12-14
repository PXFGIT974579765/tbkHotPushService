package com.pxf.first.frame.dao.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.pxf.first.frame.app.pager.Pager;

public interface IBaseDao {
	/**
     * 持久化实体
     * @param entity
     */ 
	boolean save(Object entity);
    
    /**
     * 根据主键查询实体
     * @param <T>
     * @param clazz  实体类
     * @param id     主键
     * @return */
     <T> T getById(Class<T> clazz,Object id);
     <T> boolean save(List<T> entitys);
     <T> boolean updateEntity(Class<T> entity);
     <T> List<T> queryByHql(String hql,Map<Serializable, Serializable> map);
     boolean excuteHqlByCondition(String hql, Map<Serializable, Serializable> map);
     <T> List<T> getMenu(String menuStr);
     Pager queryWithPage(Pager page);
     boolean flush();
     boolean clear();


}
