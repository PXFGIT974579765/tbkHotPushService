package com.pxf.first.frame.service.dic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pxf.first.frame.dao.dic.dao.ILoadDicDao;
import com.pxf.first.frame.enty.dic.bo.DicRoleType;
import com.pxf.first.frame.service.dic.service.ILoadDicService;
@Service
public class LoadDicServiceImpl implements ILoadDicService {
	
	@Autowired
	private ILoadDicDao dao;

	@Override
	public <T> List<T> loadDic(Class<T> cls) {
		List<T> list=this.dao.loadDic(cls);
		return list;
	}

}
