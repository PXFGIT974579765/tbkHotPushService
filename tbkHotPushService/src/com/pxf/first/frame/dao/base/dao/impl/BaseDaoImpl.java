package com.pxf.first.frame.dao.base.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.mysql.jdbc.StringUtils;
import com.pxf.first.frame.app.pager.Pager;
import com.pxf.first.frame.dao.base.dao.IBaseDao;

@Repository(value = "baseDao")
public class BaseDaoImpl implements IBaseDao {

	Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);
	// ע��ʵ�������
	@PersistenceContext
	protected EntityManager em;

	@Override
	public boolean save(Object entity) {
		boolean flag;
		try {
			em.merge(entity);
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	@Override
	public <T> T getById(Class<T> clazz, Object id) {
		return em.find(clazz, id);
	}

	@Override
	public <T> boolean save(List<T> entitys) {
		// Ϊ���������ܣ����Ƕ���ݽ��з���εı��棬�ֶ����Ƹ��µ���ݿ�
		boolean flag = false;
		try {
			// ÿ100������ݿ���д��һ��,����������ܣ���ֵ�ɸı�
			int batchSize = 100;
			int i = 0;
			for (T entity : entitys) {
				em.persist(entity);
				i++;
				if (i % batchSize == 0) {
					em.flush();
					em.clear();
				}
			}
			flag = true;
			if (logger.isDebugEnabled()) {
				logger.debug("Eao��������ʵ��ɹ���" + em.getClass().getName());
			}
		} catch (Exception e) {
			flag = false;
			logger.error("Eao��������ʵ��ʧ��", e);
		}
		return flag;

	}

	@Override
	public <T> boolean updateEntity(Class<T> entity) {
		boolean flag = false;
		try {
			em.merge(entity);
			flag = true;
		} catch (Exception e) {
			flag = false;
			logger.error("Eao����ʵ��ʧ��", e);
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> queryByHql(String hql,
			Map<Serializable, Serializable> map) {
		if (null == hql || "".equals(hql)) {
			return null;
		}
		try {
			Query query = em.createQuery(hql);
			if(map!=null){
				for (Serializable key : map.keySet()) {
					query.setParameter((String) key, map.get((String) key));
				}
			}
			List<T> list = query.getResultList();
			if (logger.isDebugEnabled()) {
				logger.debug("Eao���hql����ѯʵ�弯�ϳɹ���" + em.getClass().getName());
			}
			return list;
		} catch (Exception e) {
			logger.error("Eao���hql����ѯʵ�弯��ʧ��", e);
			return null;
		}
	}

	@Override
	public boolean excuteHqlByCondition(String hql,
			Map<Serializable, Serializable> map) {
		boolean flag;
		if (null == hql || "".equals(hql)) {
			flag = false;
		}
		try {
			Query query = em.createQuery(hql);
			for (Serializable key : map.keySet()) {
				query.setParameter((String) key, map.get(key));
			}
			query.executeUpdate();
			flag = true;
			if (logger.isDebugEnabled()) {
				logger.debug("Eao�����������ʵ��ɹ���" + em.getClass().getName());
			}
		} catch (Exception e) {
			flag = false;
			logger.error("Eao�����������ʵ��ʧ��", e);
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getMenu(String menuStr) {
		String[] menuIds = menuStr.split(",");
		StringBuffer sb = new StringBuffer();
		sb.append("from TreeNode t where t.id in (");
		for (int i = 1; i < menuIds.length; i++) {
			sb.append("?,");
		}
		sb.append("?)");
		Query query = em.createQuery(sb.toString());
		for (int i = 0; i < menuIds.length; i++) {
			query.setParameter(i + 1, menuIds[i]);
		}
		List<T> list = query.getResultList();
		return list;
	}
/*
 * ��ҳ��ѯ
 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Pager queryWithPage(Pager page) {
		String hql=page.getHql();
		if(StringUtils.isNullOrEmpty(hql)){
			throw new RuntimeException("运行时出错");
		}
		Query query=em.createQuery(hql);
		for (Object key : page.getParams().keySet()) {
			query.setParameter((String)key, page.getParams().get(key));
		}
		int count=query.getResultList().size();
		if(count>0){
			int currentPage=1;
			if(page.getCurrentPage()>0){
				currentPage=page.getCurrentPage();
			}
			query.setFirstResult((currentPage-1)*page.getPageRows());
			query.setMaxResults(page.getPageRows());
			List list=query.getResultList();
			return new Pager(currentPage, (count + page.getPageRows() - 1) / page.getPageRows(), count, page.getPageRows(), null, null, list);
		}else{
			return new Pager(0, 0, 0, page.getPageRows(), null, null, null);
		}
	}

	@Override
	public boolean flush() {
		boolean flag;
		try {
			em.flush();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean clear() {
		boolean flag;
		try {
			em.clear();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	

}
