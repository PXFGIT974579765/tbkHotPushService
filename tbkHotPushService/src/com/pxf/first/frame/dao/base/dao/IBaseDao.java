package com.pxf.first.frame.dao.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.pxf.first.frame.app.pager.Pager;

public interface IBaseDao {
	/**
     * �־û�ʵ��
     * @param entity
     */ 
	boolean save(Object entity);
    
    /**
     * ����������ѯʵ��
     * @param <T>
     * @param clazz  ʵ����
     * @param id     ����
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
