package com.pxf.first.frame.dao.dic.dao;

import java.util.List;

import com.pxf.first.frame.dao.base.dao.IBaseDao;
import com.pxf.first.frame.enty.user.bo.UserInfo;

public interface ILoadDicDao extends IBaseDao {
	<T> List<T> loadDic(Class<T> cls);

}
