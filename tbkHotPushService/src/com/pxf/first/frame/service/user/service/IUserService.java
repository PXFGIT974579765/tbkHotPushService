package com.pxf.first.frame.service.user.service;

import java.util.List;

import com.pxf.first.frame.app.pager.Pager;
import com.pxf.first.frame.app.result.model.ResultMsg;
import com.pxf.first.frame.enty.user.bo.UserInfo;
import com.pxf.first.frame.enty.user.vo.LoginParaVo;


public interface IUserService  {
	ResultMsg save(String userId,LoginParaVo userVo);
	UserInfo getById(String id);
	ResultMsg login(LoginParaVo loginPara);
	ResultMsg updatePwd(LoginParaVo loginPara, String userId);
	UserInfo updateMyInfo(LoginParaVo loginPara, String userId);
	Pager queryUsersByConditionWithPage(LoginParaVo user);
	ResultMsg deleteUser(String account);
	UserInfo updateUser(LoginParaVo user);

}
