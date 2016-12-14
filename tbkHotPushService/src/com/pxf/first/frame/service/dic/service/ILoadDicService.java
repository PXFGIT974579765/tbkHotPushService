package com.pxf.first.frame.service.dic.service;

import java.util.List;

import com.pxf.first.frame.enty.dic.bo.DicRoleType;


public interface ILoadDicService  {
	<T> List<T> loadDic(Class<T> cls);

}
