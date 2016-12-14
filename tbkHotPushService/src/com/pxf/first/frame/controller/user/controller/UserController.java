package com.pxf.first.frame.controller.user.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.pxf.first.frame.enty.product.vo.ProductVo;
import com.pxf.first.frame.enty.user.bo.UserInfo;
import com.pxf.first.frame.enty.user.vo.LoginParaVo;
import com.pxf.first.frame.service.order.service.IOrderMgrService;
import com.pxf.first.frame.service.user.service.IUserService;

/**
 * User Controller
 * 
 * @author pxf
 */

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

	private static final Logger LOG = Logger.getLogger(UserController.class);

	@Autowired
	private IUserService userService;// ע��ҵ��ӿ�
	@Autowired
	private IOrderMgrService orderService;
	@RequestMapping(value="toQueryUser.do")
	public String toQueryUser(String userId, Model medol,String token) {
		LOG.info("跳转到查询用户界面");
		return "user/queryUser";
	}

	@RequestMapping(value = "/toIndex.do")
	public ModelAndView toLogin(ProductVo vo) {
		LOG.info("跳转到电脑端网页");
		ModelAndView map=new ModelAndView();
		Pager page=this.orderService.queryUsersByConditionWithPage(vo);
		List<ProductVo> list=page.getList();
		map.addObject("products", list);
		map.addObject("currentPage", page.getCurrentPage());
		map.addObject("pageTotal", page.getPageTotal());
		map.addObject("rowsTotal", page.getRowsTotal());
		map.addObject("pageRows", page.getPageRows());
		map.setViewName("user/index_1");
		return map;
	}
	@RequestMapping(value = "/loadProducts.do")
	public ModelAndView loadProducts(ProductVo vo,HttpServletRequest request) {
		LOG.info("查询商品 ");
		ModelAndView map=new ModelAndView();
		Pager page=this.orderService.queryUsersByConditionWithPage(vo);
		map.addObject("productName", vo.getProductName());
		map.addObject("priceF", vo.getPriceF());
		map.addObject("priceT", vo.getPriceT());
		List<ProductVo> list=page.getList();
		map.addObject("products", list);
		map.addObject("currentPage", page.getCurrentPage());
		map.addObject("pageTotal", page.getPageTotal());
		map.addObject("rowsTotal", page.getRowsTotal());
		map.addObject("pageRows", page.getPageRows());
		map.setViewName("user/index_1");
		return map;
	}
	@RequestMapping(value = "/toApp.do")
	public ModelAndView toApp(ProductVo vo) {
		LOG.info("跳转到手机端网页 ");
		ModelAndView map=new ModelAndView();
		Pager page=this.orderService.queryUsersByConditionWithPage(vo);
		List<ProductVo> list=page.getList();
		map.addObject("products", list);
		map.addObject("currentPage", page.getCurrentPage());
		map.addObject("pageTotal", page.getPageTotal());
		map.addObject("rowsTotal", page.getRowsTotal());
		map.addObject("pageRows", page.getPageRows());
		map.setViewName("user/index_2");
		return map;
	}
	@RequestMapping(value = "/ajaxLoadProduct.do")
	public void ajaxLoadProduct(ProductVo vo,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info("异步请求商品信息");
		Pager page=this.orderService.queryUsersByConditionWithPage(vo);
		writeObjAsJsonObjToClient(response, page);
	}
	@RequestMapping(value = "/login.do")
	public void login(LoginParaVo loginPara, Model model,HttpServletResponse httpResponse) throws JsonGenerationException, JsonMappingException, IOException {
		  ResultMsg result = this.userService.login(loginPara);
		  writeObjAsJsonObjToClient(httpResponse, result);
	}

	@RequestMapping(value = "/index.do")
	public String getUserById(String userId, Model medol,String token) {
		LOG.info("id is :" + userId);
		UserInfo userInfo = userService.getById(userId);
		medol.addAttribute("user", userInfo);
		medol.addAttribute("token", token);
		return "user/success";
	}

	@RequestMapping(value = "/saveUser.do")
	public String saveUser(UserInfo userInfo,
			RedirectAttributes redirectAttributes) {
		userService.save(null,null);
		return "redirect:/user/userList.do";// ����ڵ�ǰ��Ŀ��·��
	}
	@RequestMapping(value = "/updatePwd.do")
	public void updatePwd(LoginParaVo loginParams,String userId,HttpServletResponse httpResponse) throws JsonGenerationException, JsonMappingException, IOException{
		 ResultMsg result = this.userService.updatePwd(loginParams,userId);
		 writeObjAsJsonObjToClient(httpResponse, result);
	}
	@RequestMapping(value = "/updateMyInfo.do")
	public void updateMyInfo(LoginParaVo loginParams,String userId,HttpServletResponse httpResponse) throws JsonGenerationException, JsonMappingException, IOException{
		UserInfo userInfo = this.userService.updateMyInfo(loginParams,userId);
		LoginParaVo user=new LoginParaVo();
		user.setPhone(userInfo.getPhone());
		user.setEmail(userInfo.getEmail());
		user.setSex(userInfo.getSex());
		user.setUserName(userInfo.getName());
		writeObjAsJsonObjToClient(httpResponse, user);
	}
}
