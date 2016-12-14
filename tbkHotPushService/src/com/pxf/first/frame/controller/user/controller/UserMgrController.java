package com.pxf.first.frame.controller.user.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pxf.first.frame.app.pager.Pager;
import com.pxf.first.frame.app.result.model.ResultMsg;
import com.pxf.first.frame.app.result.model.ResultStatusCode;
import com.pxf.first.frame.controller.base.controller.BaseController;
import com.pxf.first.frame.enty.dic.bo.DicRoleType;
import com.pxf.first.frame.enty.user.bo.UserInfo;
import com.pxf.first.frame.enty.user.vo.LoginParaVo;
import com.pxf.first.frame.service.dic.service.ILoadDicService;
import com.pxf.first.frame.service.user.service.IUserService;

/**
 * UserMgrController
 * 
 * @author pxf
 */

@Controller
@RequestMapping(value = "/userMgr")
public class UserMgrController extends BaseController {

	private static final Logger LOG = Logger.getLogger(UserMgrController.class);

	@Autowired
	private ILoadDicService service;
	@Autowired
	private IUserService userService;
	@RequestMapping(value = "/loadRoleDic.do")
	public void loadRoleDic(HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		List<DicRoleType> roleDic=this.service.loadDic(DicRoleType.class);
		writeObjAsJsonObjToClient(response, roleDic);
	}
	@RequestMapping(value = "/addUser.do")
	public void addUser(String userId,LoginParaVo user,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		ResultMsg result=this.userService.save(userId,user);
		writeObjAsJsonObjToClient(response, result);
	}
	@RequestMapping(value = "/deleteUser.do")
	public void deleteUser(String account,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		ResultMsg result=this.userService.deleteUser(account);
		writeObjAsJsonObjToClient(response, result);
	}
	@RequestMapping(value = "/updateUser.do")
	public void updateUser(LoginParaVo userVo,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		UserInfo user=this.userService.updateUser(userVo);
		writeObjAsJsonObjToClient(response, user);
	}
	@RequestMapping(value = "/queryByCondition.do")
	public void queryByCondition(LoginParaVo user,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		Pager page=this.userService.queryUsersByConditionWithPage(user);
		writeObjAsJsonObjToClient(response, page);
	}
}
