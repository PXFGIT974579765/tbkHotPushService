package com.pxf.first.frame.dao.user.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mysql.jdbc.StringUtils;
import com.pxf.first.frame.app.pager.Pager;
import com.pxf.first.frame.dao.base.dao.impl.BaseDaoImpl;
import com.pxf.first.frame.dao.user.dao.IUserDao;
import com.pxf.first.frame.enty.user.bo.UserInfo;
import com.pxf.first.frame.enty.user.vo.LoginParaVo;


@Repository
public class UserDaoImpl extends BaseDaoImpl implements IUserDao {

	private final static int PAGE_SIZE_DEFAULT=10;
	private final static int CURRENT_PAGE_DEFAULT=1;
	@Override
	public void test() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserInfo getByAccount(String account,String isValid) {
		StringBuffer sb=new StringBuffer();
		sb.append("From UserInfo u where u.account=:account");
		Map<Serializable, Serializable> map=new HashMap<Serializable, Serializable>();
		map.put("account", account);
		if(isValid==UserInfo.IS_VALID){
			sb.append(" and u.isValid=:isValid");
			map.put("isValid",UserInfo.IS_VALID);
		}
		if(isValid==UserInfo.NO_VALID){
			sb.append(" and u.isValid=:isValid");
			map.put("isValid",UserInfo.NO_VALID);
		}
		List<UserInfo> userList=queryByHql(sb.toString(), map);
		if(userList.size()!=0){
			return userList.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pager queryUsersByConditionWithPage(LoginParaVo user,String isValid) {
		Pager<UserInfo> page=new Pager<UserInfo>();
		StringBuffer sb=new StringBuffer();
		Map<String , Object> params = new HashMap<String , Object>();
		sb.append("select u From UserInfo u where 1=1");
		if(!StringUtils.isNullOrEmpty(user.getUserName())){
			sb.append(" and u.name like:name");
			params.put("name", "%"+user.getUserName().trim()+"%");
		}
		if(!StringUtils.isNullOrEmpty(user.getAccount())){
			sb.append(" and u.account like:account");
			params.put("account", "%"+user.getAccount().trim()+"%");
		}
		if(!StringUtils.isNullOrEmpty(user.getSex())){
			sb.append(" and u.sex =:sex");
			params.put("sex", user.getSex().trim());
		}
		if(!StringUtils.isNullOrEmpty(user.getRole())){
			sb.append(" and u.roleType =:roleType");
			params.put("roleType", user.getRole().trim());
		}
		if(!StringUtils.isNullOrEmpty(user.getEmail())){
			sb.append(" and u.email like:email");
			params.put("email", "%"+user.getEmail().trim()+"%");
		}
		if(!StringUtils.isNullOrEmpty(user.getPhone())){
			sb.append(" and u.phone like:phone");
			params.put("phone", "%"+user.getPhone().trim()+"%");
		}
		if(isValid==UserInfo.IS_VALID){
			sb.append(" and u.isValid=:isValid");
			params.put("isValid", UserInfo.IS_VALID);
		}
		if(isValid==UserInfo.NO_VALID){
			sb.append(" and u.isValid=:isValid");
			params.put("isValid", UserInfo.NO_VALID);
		}
		if(user.getPageRows()==0){
			user.setPageRows(PAGE_SIZE_DEFAULT);
		}
		if(user.getCurrentPage()==0){
			user.setCurrentPage(CURRENT_PAGE_DEFAULT);
		}
		page.setHql(sb.toString());
		page.setParams(params);
		page.setPageRows(user.getPageRows());
		page.setCurrentPage(user.getCurrentPage());
		page=queryWithPage(page);
		return page;
	}

}
