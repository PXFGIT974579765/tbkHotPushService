package com.pxf.first.frame.service.order.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pxf.first.frame.app.pager.Pager;
import com.pxf.first.frame.app.result.model.ResultMsg;
import com.pxf.first.frame.enty.dic.bo.DicUser;
import com.pxf.first.frame.enty.product.bo.Buget;
import com.pxf.first.frame.enty.product.bo.FileInfo;
import com.pxf.first.frame.enty.product.bo.OrderBasic;
import com.pxf.first.frame.enty.product.bo.OwnerInfo;
import com.pxf.first.frame.enty.product.vo.OrderVo;
import com.pxf.first.frame.enty.product.vo.ProductVo;
import com.pxf.first.frame.enty.user.bo.UserInfo;



public interface IOrderMgrService  {
	ResultMsg addOrderBaisic(OrderVo orderVo,String account,String saveType) throws IllegalAccessException, InvocationTargetException;
	ResultMsg saveOrderBaisic(OrderVo orderVo,String account,String saveType,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException;
	ResultMsg loadUsersByRole(String roleId);
	ResultMsg editStageInfo(OrderVo order,String detailId);
	ResultMsg loadEmailByAccount(String account);
	Pager queryUsersByConditionWithPage(ProductVo vo);
	OrderBasic queryOrderBasicByOrderId(String orderId);
	List<FileInfo> loadFilesByOrderId(String orderId,String stageId);
	List<Buget> loadBugetsByOrderId(String orderId,String stageId);
	OwnerInfo queryOwnerByOwnerId(String ownerId);
	UserInfo queryUserByAccount(String account);
	List<DicUser> loadUserDic();
	Buget queryByBugetId(String bugetId);
	FileInfo queryByFileId(String fileId);
	ResultMsg addBugetItem(Buget buget);
	ResultMsg saveFile(FileInfo file);
	ResultMsg deleteFile(String fileId);
	ResultMsg deleteBuget(String bugetId);

}
