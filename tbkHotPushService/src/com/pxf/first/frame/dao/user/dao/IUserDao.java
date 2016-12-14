package com.pxf.first.frame.dao.user.dao;

import java.util.List;

import com.pxf.first.frame.app.pager.Pager;
import com.pxf.first.frame.dao.base.dao.IBaseDao;
import com.pxf.first.frame.enty.user.bo.UserInfo;
import com.pxf.first.frame.enty.user.vo.LoginParaVo;

public interface IUserDao extends IBaseDao {
	void test();
	UserInfo getByAccount(String account,String isValid);
	Pager queryUsersByConditionWithPage(LoginParaVo user,String isValid);
}
