package com.pxf.first.frame.controller.order.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mysql.jdbc.StringUtils;
import com.pxf.first.frame.app.pager.Pager;
import com.pxf.first.frame.app.result.model.ResultMsg;
import com.pxf.first.frame.app.system.Config;
import com.pxf.first.frame.controller.base.controller.BaseController;
import com.pxf.first.frame.enty.dic.bo.DicOrderType;
import com.pxf.first.frame.enty.dic.bo.DicOwnerType;
import com.pxf.first.frame.enty.dic.bo.DicRoleType;
import com.pxf.first.frame.enty.dic.bo.DicStage;
import com.pxf.first.frame.enty.dic.bo.DicState;
import com.pxf.first.frame.enty.dic.bo.DicUser;
import com.pxf.first.frame.enty.product.bo.Buget;
import com.pxf.first.frame.enty.product.bo.FileInfo;
import com.pxf.first.frame.enty.product.bo.OrderBasic;
import com.pxf.first.frame.enty.product.bo.OrderDetail;
import com.pxf.first.frame.enty.product.bo.OwnerInfo;
import com.pxf.first.frame.enty.product.vo.OrderVo;
import com.pxf.first.frame.enty.product.vo.ProductVo;
import com.pxf.first.frame.enty.user.vo.LoginParaVo;
import com.pxf.first.frame.service.dic.service.ILoadDicService;
import com.pxf.first.frame.service.order.service.IOrderMgrService;


@Controller
@RequestMapping(value = "/order")
public class OrderController extends BaseController {

	private static final Logger LOG = Logger.getLogger(OrderController.class);

	@Autowired
	private ILoadDicService service;// ע��ҵ��ӿ�
	@Autowired
	private IOrderMgrService orderService;// ע��ҵ��ӿ�

	@RequestMapping(value = "/toAddOrder.do")
	public String addUser(String userId,LoginParaVo user,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info("跳转到添加订单页面");
		return "order/addOrder";
	}
	/**
	 * 加载订单类型字典
	 */
	@RequestMapping(value = "/orderType.do")
	public void loadOrderTypeDic(HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info("载订单类型字典");
		List<DicOrderType> orderTypeDic=this.service.loadDic(DicOrderType.class);
		writeObjAsJsonObjToClient(response, orderTypeDic);
	}
	/**
	 * 打开生成预算模板窗口
	 */
	@RequestMapping(value = "/openWindow.do")
	public String openBugetTemplateWindow(HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info("打开生成预算模板窗口");
		return "order/BugetTemplate";
	}
	/**
	 * 打开阶段信息窗口
	 */
	@RequestMapping(value = "/openStageWindow.do")
	public ModelAndView openStageWindow(String orderId,String detailId,String stageId,String type,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info(" 打开阶段信息窗口");
		ModelAndView map =new ModelAndView();
		map.addObject("type", type);
		map.addObject("stageId", stageId);
		map.addObject("orderId", orderId);
		map.addObject("detailId", detailId);
		OrderBasic order=this.orderService.queryOrderBasicByOrderId(orderId);
		if(order!=null){
			map.addObject("order", order);
			if(stageId.equals(Config.STAGE_DDFQ)){
				OwnerInfo owner=this.orderService.queryOwnerByOwnerId(order.getOrderOwnerId());
				map.addObject("owner", owner);
			}else{
				for(OrderDetail detail:order.getDetailList()){
					if(detail.getCurStageId().equals(stageId)){
						map.addObject("detail",detail);
						break;
					}
				}
			}
		}
		List<DicOwnerType> ownerTypeDic=this.service.loadDic(DicOwnerType.class);
		map.addObject("ownerTypeDic", getMapOfObj(ownerTypeDic));
		List<DicStage> stageDic=this.service.loadDic(DicStage.class);
		map.addObject("stageDic",getMapOfObj(stageDic));
		List<DicOrderType> orderTypeDic=this.service.loadDic(DicOrderType.class);
		map.addObject("orderTypeDic", getMapOfObj(orderTypeDic));
		map.setViewName("order/StageInfo");
		List<DicUser> userDic=this.orderService.loadUserDic();
		map.addObject("userDic", getMapOfObj(userDic));
		return map;
	}
	/**
	 * 打开单条预算窗口
	 */
	@RequestMapping(value = "/openBugetItemWindow.do")
	public ModelAndView openBugetItemWindow(HttpServletResponse response,String bugetId,String orderId,String myAccount) throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info(" 打开单条预算窗口");
		ModelAndView map =new ModelAndView();
		Buget buget;
		if(!StringUtils.isNullOrEmpty(bugetId)){
			buget=this.orderService.queryByBugetId(bugetId);
		}else{
			buget=new Buget();
			buget.setOrderId(orderId);
			buget.setBugetPerAcc(myAccount);
		}
		map.addObject("buget",buget);
		List<DicStage> stageDic=this.service.loadDic(DicStage.class);
		map.addObject("stageDic",stageDic);
		map.setViewName("order/BugetItem");
		return map;
	}
	/**
	 * 打开附件上传窗口
	 */
	@RequestMapping(value = "/openFileUploadWindow.do")
	public ModelAndView openFileUploadWindow(HttpServletResponse response,String fileId,String orderId,String myAccount) throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info(" 打开附件上传窗口");
		ModelAndView map =new ModelAndView();
		FileInfo file;
		if(!StringUtils.isNullOrEmpty(fileId)){
			file=this.orderService.queryByFileId(fileId);
		}else{
			file=new FileInfo();
			file.setOrderBasicId(orderId);
			file.setFilePerAcc(myAccount);
		}
		map.addObject("file",file);
		List<DicStage> stageDic=this.service.loadDic(DicStage.class);
		map.addObject("stageDic",stageDic);
		map.setViewName("order/FileUpload");
		return map;
	}
	/**
	 *加载业主类型字典
	 */
	@RequestMapping(value = "/ownerType.do")
	public void loadOwnerTypeDic(HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info("加载业主类型字典");
		List<DicOwnerType> ownerTypeDic=this.service.loadDic(DicOwnerType.class);
		writeObjAsJsonObjToClient(response, ownerTypeDic);
	}
	/**
	 * 加载阶段类型字典
	 */
	@RequestMapping(value = "/stage.do")
	public void loadStageId(HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info("加载阶段类型字典");
		List<DicStage> ownerTypeDic=this.service.loadDic(DicStage.class);
		writeObjAsJsonObjToClient(response, ownerTypeDic);
	}
	/**
	 *加载状态字典
	 */
	@RequestMapping(value = "/state.do")
	public void loadStateId(HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info("加载状态字典");
		List<DicState> ownerTypeDic=this.service.loadDic(DicState.class);
		writeObjAsJsonObjToClient(response, ownerTypeDic);
	}
	/**
	 * 添加预算信息
	 */
	@RequestMapping(value = "/addBugetItem.do")
	public void addBugetItem(HttpServletResponse response,Buget buget) throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info("添加预算信息");
		ResultMsg result=this.orderService.addBugetItem(buget);
		writeObjAsJsonObjToClient(response, result);
	}
	/**
	 * 保存文件
	 */
	@RequestMapping(value = "/saveFile.do")
	public void saveFile(HttpServletResponse response,FileInfo file) throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info("保存文件");
		ResultMsg result=this.orderService.saveFile(file);
		writeObjAsJsonObjToClient(response, result);
	}
	/**
	 * 删除附件
	 */
	@RequestMapping(value = "/deleteFile.do")
	public void deleteFile(HttpServletResponse response,String fileId) throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info("删除附件");
		ResultMsg result=this.orderService.deleteFile(fileId);
		writeObjAsJsonObjToClient(response, result);
	}
	/**
	 * 删除预算信息
	 */
	@RequestMapping(value = "/deleteBuget.do")
	public void deleteBuget(HttpServletResponse response,String bugetId) throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info("删除预算信息");
		ResultMsg result=this.orderService.deleteBuget(bugetId);
		writeObjAsJsonObjToClient(response, result);
	}
	
	/**
	 * 添加订单信息
	 */
	@RequestMapping(value = "/addOrderBasic.do")
	public void addOrderBasic(OrderVo order,String myAccount,String saveType,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException, IllegalAccessException, InvocationTargetException {
		LOG.info("添加订单信息");
		ResultMsg result=this.orderService.addOrderBaisic(order,myAccount,saveType);
		writeObjAsJsonObjToClient(response, result);
	}
	/**
	 * 保存订单信息
	 */
	@RequestMapping(value = "/saveOrderBasic.do")
	public void saveOrderBasic(OrderVo order,String myAccount,String saveType,HttpServletResponse response,HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException, IllegalAccessException, InvocationTargetException {
		LOG.info(" 保存订单信息");
		ResultMsg result=this.orderService.saveOrderBaisic(order,myAccount,saveType,request);
		writeObjAsJsonObjToClient(response, result);
	}
	/**
	 *编辑阶段信息
	 */
	@RequestMapping(value = "/editStageInfo.do")
	public void editStageInfo(OrderVo order,String detailId,HttpServletResponse response,HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException, IllegalAccessException, InvocationTargetException {
		LOG.info("编辑阶段信息");
		ResultMsg result=this.orderService.editStageInfo(order,detailId);
		writeObjAsJsonObjToClient(response, result);
	}
	/**
	 * 跳转到查询订单页面
	 */
	@RequestMapping(value = "/toQueryOrder.do")
	public String toQueryOrder(HttpServletResponse response,String isSelf,Model model) throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info("跳转到查询订单页面");
		model.addAttribute("isSelf", isSelf);
		return "order/queryOrder";
	}
	/**
	 * ͨ加载用户角色
	 */
	@RequestMapping(value = "/loadUsersByRole.do")
	public void loadUsersByRole(HttpServletResponse response,String roleId,Model model) throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info("ͨ加载用户角色");
		ResultMsg result=this.orderService.loadUsersByRole(roleId);
		writeObjAsJsonObjToClient(response, result);
	}
	/*
	 *通过条件查询订单信息
	 */
	@RequestMapping(value = "/queryByCondition.do")
	public void queryByCondition(ProductVo vo) throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info("ͨ通过条件查询订单信息");
		Pager page=this.orderService.queryUsersByConditionWithPage(vo);
//		writeObjAsJsonObjToClient(response, page);
	}
	/*
	 * 加载邮件
	 */
	@RequestMapping(value = "/loadEmail.do")
	public void loadEmailByAccount(String account,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		LOG.info("ͨ加载邮件");
		ResultMsg result=this.orderService.loadEmailByAccount(account);
		writeObjAsJsonObjToClient(response, result);
	}
	@RequestMapping(value = "/queryOrderById.do")
	public ModelMap queryOrderById(String orderId,HttpServletResponse response,ModelMap map) throws JsonGenerationException, JsonMappingException, IOException {
//		Pager page=this.orderService.queryUsersByConditionWithPage(orderId);
//		writeObjAsJsonObjToClient(response, page);
		return null;
	}
	/*
	 *跳转到订单信息
	 * flag表示是点击查看还是点击编辑
	 * flag=query表示点击的是编号
	 * flag=edit便是点击的是代办事项
	 */
	@RequestMapping(value = "/toEditOrder.do")
	public ModelAndView toEditOrder(String orderId,String nextStageId,String flag,String myAccount,HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		ModelAndView map =new ModelAndView();
		OrderBasic orderBo=this.orderService.queryOrderBasicByOrderId(orderId);
		map.addObject("orderBasic", orderBo);
		String ownerId=orderBo.getOrderOwnerId();
		OwnerInfo owner=this.orderService.queryOwnerByOwnerId(ownerId);
		map.addObject("owner", owner);
		List<DicOwnerType> ownerTypeDic=this.service.loadDic(DicOwnerType.class);
		map.addObject("ownerTypeDic", getMapOfObj(ownerTypeDic));
		List<DicStage> stageDic=this.service.loadDic(DicStage.class);
		map.addObject("stageDic",getMapOfObj(stageDic));
		List<DicOrderType> orderTypeDic=this.service.loadDic(DicOrderType.class);
		map.addObject("orderTypeDic", getMapOfObj(orderTypeDic));
		if(nextStageId.equals(Config.STAGE_DDFQ)){
			map.setViewName("order/addOrder");
			return map;
		}
		map.addObject("nextStageId", nextStageId);
		map.addObject("flag",flag);
		map.addObject("myAccount",myAccount);
		
		List<DicUser> userDic=this.orderService.loadUserDic();
		map.addObject("userDic", getMapOfObj(userDic));
		
		
		List<DicRoleType> roleTypeDic=this.service.loadDic(DicRoleType.class);
		map.addObject("roleTypeDic", getMapOfObj(roleTypeDic));
	
		List<DicState> stateDic=this.service.loadDic(DicState.class);
		map.addObject("stateDic",getMapOfObj(stateDic));
		List<OrderDetail> orderDetailList = orderBo.getDetailList();
		List<FileInfo> files = new ArrayList<FileInfo>();
		List<Buget> bugets = new ArrayList<Buget>();
		OrderDetail odt=null;
		for (OrderDetail detail : orderDetailList) {
			String fileStatus = detail.getFileStatus();
			String bugetStatus = detail.getBugetStatus();
			if(detail.getCurStageId().equals(nextStageId)){
				odt=detail;
			}
			if (!fileStatus.equals("0")) {
				List<FileInfo> fileList = this.orderService.loadFilesByOrderId(
						orderId, detail.getCurStageId());
				files.addAll(fileList);
			}
			if (!bugetStatus.equals("0")) {
				List<Buget> bugetList = this.orderService.loadBugetsByOrderId(
						orderId, detail.getCurStageId());
				bugets.addAll(bugetList);
			}
		}
		map.addObject("files", files);
		map.addObject("bugets", bugets);
		if(flag.equals("edit")){
			if(odt==null){
				odt=new OrderDetail();
				odt.setCurStageId(nextStageId);
				odt.setCurPerAcc(myAccount);
				odt.setCurStateId("0");
				orderDetailList.add(odt);
			}
		}
		map.addObject("orderDetail", orderDetailList);
		map.setViewName("order/OrderDetail");
		return map;
	}
}
