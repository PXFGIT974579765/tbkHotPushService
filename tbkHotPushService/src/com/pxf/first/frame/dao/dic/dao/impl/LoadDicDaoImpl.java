package com.pxf.first.frame.dao.dic.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pxf.first.frame.dao.base.dao.impl.BaseDaoImpl;
import com.pxf.first.frame.dao.dic.dao.ILoadDicDao;
@Repository
public class LoadDicDaoImpl extends BaseDaoImpl implements ILoadDicDao {
	@Override
	public <T> List<T> loadDic(Class<T> cls) {
		String clsName=cls.getName();
		String hql="From "+clsName+" u";
		List<T> list=queryByHql(hql,null);
		return list;
	}

}
