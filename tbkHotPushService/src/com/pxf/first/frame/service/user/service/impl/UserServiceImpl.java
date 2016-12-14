package com.pxf.first.frame.service.user.service.impl;


import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.mysql.jdbc.StringUtils;
import com.pxf.first.frame.app.pager.Pager;
import com.pxf.first.frame.app.result.model.ResultMsg;
import com.pxf.first.frame.app.result.model.ResultStatusCode;
import com.pxf.first.frame.app.security.jwt.auth.Audience;
import com.pxf.first.frame.app.security.jwt.auth.JwtHelper;
import com.pxf.first.frame.app.utils.Md5Util;
import com.pxf.first.frame.app.utils.IDUtil;
import com.pxf.first.frame.dao.user.dao.IUserDao;
import com.pxf.first.frame.enty.dic.bo.DicAuth;
import com.pxf.first.frame.enty.dic.bo.DicRoleType;
import com.pxf.first.frame.enty.user.bo.UserInfo;
import com.pxf.first.frame.enty.user.vo.LoginParaVo;
import com.pxf.first.frame.service.user.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserDao userDao;
	@Autowired
    private Audience audienceEntity;
   /*
    * 添加用户
    */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
    @TriggersRemove(cacheName="userCache",removeAll=true)//清除缓存
	public ResultMsg save(String userId,LoginParaVo userVo) {
		ResultMsg resultMsg;
		UserInfo userBo=this.userDao.getByAccount(userVo.getAccount(),UserInfo.ALL_VALID);
		if(userBo!=null){
			resultMsg = new ResultMsg(ResultStatusCode.USER_EXIST.getCode(),   
                    ResultStatusCode.USER_EXIST.getMsg(), null);  
            return resultMsg;
		}
		UserInfo user=new UserInfo();
		user.setId(IDUtil.uuidTo32length());
		user.setName(userVo.getUserName());
		user.setAccount(userVo.getAccount());
		user.setPassword(Md5Util.getMD5(userVo.getPswd()));
		user.setPhone(userVo.getPhone());
		user.setEmail(userVo.getEmail());
		user.setSex(userVo.getSex());
		user.setRoleType(userVo.getRole());
		user.setIsValid(1+"");
		userDao.save(user);
		resultMsg=new ResultMsg(ResultStatusCode.OK.getCode(),
    			ResultStatusCode.OK.getMsg(), null);
    	return resultMsg;

	}

	/*
	 * 通过id获取用户信息
	*/
	@Override
	@Cacheable(cacheName="userCache")//缓存数据
	public UserInfo getById(String id) {
		UserInfo userInfo=userDao.getById(UserInfo.class, id);
		DicRoleType dicRoleType=userDao.getById(DicRoleType.class, userInfo.getRoleType());
		userInfo.setRoleName(dicRoleType.getRoleName());
		return userInfo;
	}

	/*
	 * 用户登录
	 */
	@Override
	public ResultMsg login(LoginParaVo loginPara) {
		ResultMsg resultMsg;
		UserInfo user=this.userDao.getByAccount(loginPara.getAccount(),UserInfo.IS_VALID);
		if(user==null){
			resultMsg = new ResultMsg(ResultStatusCode.INVALID_LOGIN.getCode(),   
                    ResultStatusCode.INVALID_LOGIN.getMsg(), null);  
            return resultMsg;
		}
		else  
        {  
            String md5Password = Md5Util.getMD5(loginPara.getPswd());  
              
            if (md5Password.compareTo(user.getPassword()) != 0)  
            {  
                resultMsg = new ResultMsg(ResultStatusCode.INVALID_LOGIN.getCode(),  
                        ResultStatusCode.INVALID_LOGIN.getMsg(), null);  
                return resultMsg;  
            }  
        }
		DicAuth dicAuth=userDao.getById(DicAuth.class, user.getRoleType());
		 //拼装accessToken  
        String accessToken = JwtHelper.createJWT(loginPara.getAccount(),user.getId(),dicAuth.getAuthStr(),
                 audienceEntity.getClientId(), audienceEntity.getName(),  
                 audienceEntity.getExpiresSecond() * 1000, audienceEntity.getBase64Secret());  
          
        //返回accessToken  
//        AccessToken accessTokenEntity = new AccessToken();  
//        accessTokenEntity.setAccess_token(accessToken);  
//        accessTokenEntity.setExpires_in(audienceEntity.getExpiresSecond());  
//        accessTokenEntity.setToken_type("bearer");  
        resultMsg = new ResultMsg(ResultStatusCode.OK.getCode(),   
                ResultStatusCode.OK.getMsg(),accessToken,null);  
        return resultMsg; 
	}

    //修改密码
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public ResultMsg updatePwd(LoginParaVo loginPara,String userId) {
		ResultMsg resultMsg;
		UserInfo user = this.userDao.getById(UserInfo.class,userId);
		if (user == null) {
			resultMsg = new ResultMsg(ResultStatusCode.SYSTEM_ERR.getCode(),
					ResultStatusCode.SYSTEM_ERR.getMsg(), null);
			return resultMsg;
		}
		String md5Password = Md5Util.getMD5(loginPara.getPswd());
		if (md5Password.compareTo(user.getPassword()) != 0) {
			resultMsg = new ResultMsg(ResultStatusCode.UPDATE_PWD.getCode(),
					ResultStatusCode.UPDATE_PWD.getMsg(), null);
			return resultMsg;
		}
		if(loginPara.getNewPwd().compareTo(loginPara.getNewPwd1())!=0){
        	resultMsg=new ResultMsg(ResultStatusCode.UPDATE_NEW_PWD.getCode(),
        			ResultStatusCode.UPDATE_NEW_PWD.getMsg(), null);
        	return resultMsg;
        }
		user.setPassword(Md5Util.getMD5(loginPara.getNewPwd()));
		userDao.save(user);
		resultMsg=new ResultMsg(ResultStatusCode.OK.getCode(),
    			ResultStatusCode.OK.getMsg(), null);
    	return resultMsg;
	}
	//修改个人信息
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public UserInfo updateMyInfo(LoginParaVo loginPara,String userId) {
		UserInfo user = this.userDao.getById(UserInfo.class,userId);
		if (user == null) {
			return null;
		}
		user.setEmail(loginPara.getEmail());
		user.setName(loginPara.getUserName());
		user.setSex(loginPara.getSex());
		user.setPhone(loginPara.getPhone());
		userDao.save(user);
		return user;
	}

   //通过条件对用户进行分页查询
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Pager queryUsersByConditionWithPage(LoginParaVo user) {
		Pager page=this.userDao.queryUsersByConditionWithPage(user,UserInfo.IS_VALID);
		return page;
	}
    //删除用户
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public ResultMsg deleteUser(String account) {
		ResultMsg resultMsg;
		UserInfo user=this.userDao.getByAccount(account,UserInfo.IS_VALID);
		if(user==null){
			resultMsg=new ResultMsg(ResultStatusCode.SYSTEM_ERR.getCode(), 
					ResultStatusCode.SYSTEM_ERR.getMsg(), null);
			return resultMsg;
		}
		user.setIsValid(0+"");
		this.userDao.save(user);
		resultMsg=new ResultMsg(ResultStatusCode.OK.getCode(), 
				ResultStatusCode.OK.getMsg(), null);
		return resultMsg;
	}
/*
 * 修改用户
 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public UserInfo updateUser(LoginParaVo userVo) {
		UserInfo user = this.userDao.getById(UserInfo.class,userVo.getId());
		if (user == null) {
			return null;
		}
		user.setEmail(userVo.getEmail());
		user.setName(userVo.getUserName());
		user.setSex(userVo.getSex());
		user.setAccount(userVo.getAccount());
		user.setRoleType(userVo.getRole());
		user.setPhone(userVo.getPhone());
		userDao.save(user);
		return user;
	}

}
