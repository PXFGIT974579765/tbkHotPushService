package com.pxf.first.frame.service.order.service.impl;


import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.ehcache.annotations.Cacheable;
import com.mysql.jdbc.StringUtils;
import com.pxf.first.frame.app.listener.EmailListener;
import com.pxf.first.frame.app.pager.Pager;
import com.pxf.first.frame.app.result.model.ResultMsg;
import com.pxf.first.frame.app.result.model.ResultStatusCode;
import com.pxf.first.frame.app.security.jwt.auth.Audience;
import com.pxf.first.frame.app.system.Config;
import com.pxf.first.frame.app.thread.EmailThread;
import com.pxf.first.frame.app.utils.BeanUtils;
import com.pxf.first.frame.app.utils.DateUtil;
import com.pxf.first.frame.app.utils.IDUtil;
import com.pxf.first.frame.dao.order.dao.IOrderMgrDao;
import com.pxf.first.frame.enty.dic.bo.DicUser;
import com.pxf.first.frame.enty.email.vo.Email;
import com.pxf.first.frame.enty.product.bo.Buget;
import com.pxf.first.frame.enty.product.bo.FileInfo;
import com.pxf.first.frame.enty.product.bo.OrderBasic;
import com.pxf.first.frame.enty.product.bo.OrderDetail;
import com.pxf.first.frame.enty.product.bo.OwnerInfo;
import com.pxf.first.frame.enty.product.vo.Detail;
import com.pxf.first.frame.enty.product.vo.OrderVo;
import com.pxf.first.frame.enty.product.vo.OwnerVo;
import com.pxf.first.frame.enty.product.vo.ProductVo;
import com.pxf.first.frame.enty.user.bo.UserInfo;
import com.pxf.first.frame.service.order.service.IOrderMgrService;

@Service
public class OrderMgrServiceImpl implements IOrderMgrService {
	
	@Autowired
	private IOrderMgrDao orderDao;
	@Autowired
    private Audience audienceEntity;
	/*
	 * ���涩������Ϣ
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,noRollbackFor = Exception.class)
	public ResultMsg saveOrderBaisic(OrderVo orderVo, String account,String saveType,HttpServletRequest request) throws IllegalAccessException,InvocationTargetException {
		ResultMsg result;
		String curStageId=orderVo.getCurStageId();
		String orderId=orderVo.getOrderId();
		OrderBasic orderBo=this.orderDao.getById(OrderBasic.class, orderId);
		orderBo.setCurStageId(curStageId);
		orderBo.setCurPerAcc(account);
		String nextPerStr="";
		String[] nextPerAccs;
		if(saveType.equals(Config.SAVE_TYPE_TEMP)){
			orderBo.setNextPerAcc(account);
			orderBo.setNextStageId(curStageId);
		}else{
			nextPerAccs=request.getParameterValues("nextPerAcc");
			for (int i = 0; i < nextPerAccs.length; i++) {
				nextPerStr+=nextPerAccs[i];
				if(i!=(nextPerAccs.length-1)){
					nextPerStr+=",";
				}
			}
			orderBo.setNextPerAcc(nextPerStr);
			orderBo.setNextStageId(orderVo.getNextStageId());
			if(orderVo.getCurStageId().equals(Config.STAGE_JG)){
				orderBo.setFinishTime(DateUtil.stubDateTime());
				orderBo.setCurStateId(Config.STATE_YJJS);
			}
		}
		this.orderDao.save(orderBo);
		List<OrderDetail> details=orderBo.getDetailList();
		String detailId="";
		OrderDetail detail = null;
		for (OrderDetail d:details) {
			if(d.getCurStageId().equals(curStageId)){
				detailId=d.getId();
				detail=d;
			}
		}
		String time=DateUtil.stubDateTime();
		if(StringUtils.isNullOrEmpty(detailId)){
			detail=new OrderDetail();
			detail.setId(IDUtil.serialId());
			detail.setBeginTime(time);
		}
		detail.setCurPerAcc(account);
		detail.setCurStageId(curStageId);
		detail.setRemark(orderVo.getRemark());
		
		//������Ϣ״̬
		String hql="from FileInfo f where f.stageId=:stageId and f.orderBasicId=:orderBasicId";
		Map<Serializable, Serializable> map=new HashMap<Serializable, Serializable>();
		map.put("stageId", curStageId);
		map.put("orderBasicId", orderId);
		List<FileInfo> files=this.orderDao.queryByHql(hql, map);
		if(files!=null&&files.size()>0){
			detail.setFileStatus(Config.FILE_STATUS_HAS_FILE);
		}else{
			detail.setFileStatus(Config.FILE_STATUS_NO_FILE);
		}
		//Ԥ����Ϣ״̬
		hql="from Buget b where b.stageId=:stageId and b.orderId=:orderId";
		Map<Serializable, Serializable> map1=new HashMap<Serializable, Serializable>();
		map1.put("stageId", curStageId);
		map1.put("orderId", orderId);
		List<Buget> bugets=this.orderDao.queryByHql(hql, map1);
		if(bugets!=null&&bugets.size()>0){
			detail.setBugetStatus(Config.BUGET_STATUS_HAS_BUGET);
		}else{
			detail.setBugetStatus(Config.BUGET_STATUS_NO_BUGET);
		}
		//
		if(saveType.equals(Config.SAVE_TYPE_TEMP)){
			detail.setFinishTime("");
			detail.setCurStateId(Config.STATE_ZZJX);
		}else{
			detail.setFinishTime(time);
			detail.setCurStateId(Config.STATE_YJJS);
		}
		detail.setOrderBasic(orderBo);
		this.orderDao.save(detail);
		Detail reDetail=new Detail();
		reDetail.setCurStageId(detail.getCurStageId());
		reDetail.setCurStateId(detail.getCurStateId());
		reDetail.setBeginTime(detail.getBeginTime());
		reDetail.setFinishTime(detail.getFinishTime());
		reDetail.setId(detail.getId());
		reDetail.setCurPerAcc(detail.getCurPerAcc());
		result=new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), reDetail);
		return result;
	}
	/*
	 * ��Ӷ�������Ϣ
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,noRollbackFor = Exception.class)
	public ResultMsg addOrderBaisic(OrderVo orderVo,String account,String saveType) throws IllegalAccessException, InvocationTargetException {
		ResultMsg result;
		OwnerVo ownerVo=orderVo.getOwner();
		OwnerInfo ownerBo=new OwnerInfo();
		BeanUtils.copyProperties(ownerBo, ownerVo);
		if(StringUtils.isNullOrEmpty(ownerVo.getOwnerId())){
			String ownerId=IDUtil.uuidTo32length();
			ownerBo.setOwnerId(ownerId);
		}
		this.orderDao.save(ownerBo);
		if(StringUtils.isNullOrEmpty(ownerBo.getOwnerId())){
			result=new ResultMsg(ResultStatusCode.OWNER_ADD.getCode(), ResultStatusCode.OWNER_ADD.getMsg(), null);
			return result;
		}
		OrderBasic orderBo=null;
		OrderDetail detail = null;
		//����id
		if(StringUtils.isNullOrEmpty(orderVo.getOrderId())){
			orderBo=new OrderBasic();
			orderBo.setOrderId(IDUtil.serialId());
			orderBo.setStartTime(DateUtil.stubDateTime());
		}else{
			orderBo=this.orderDao.getById(OrderBasic.class , orderVo.getOrderId());
		}
		List<String> skipPros=new ArrayList<String>();
		skipPros.add("startTime");
		skipPros.add("orderId");
		BeanUtils.copySkipProps(orderBo, orderVo,skipPros);
		//ҵ��id
		orderBo.setOrderOwnerId(ownerBo.getOwnerId());
		//��������׶�
		orderBo.setCurStageId(Config.STAGE_DDFQ);
		//0��ʾ�������ڽ��У�1��ʾ��������
		orderBo.setCurStateId(Config.STATE_ZZJX);
		orderBo.setEstaPerAcc(account);
		//�ͻ��˴�������Ĭ��������һ�������˺���һ����׶ε�,������ݴ�Ͱ���һ������������Ϊ��ǰ�����ˣ���һ�׶α�Ϊ��ǰ�׶�
		if(saveType.equals(Config.SAVE_TYPE_TEMP)){
			orderBo.setNextPerAcc(account);
			orderBo.setNextStageId(Config.STAGE_DDFQ);
		}
		this.orderDao.save(orderBo);
		this.orderDao.flush();
		this.orderDao.clear();
		/*
		 *=================================
		 *һ����detailList
		 */
		//˵���������ӵ�
		if(StringUtils.isNullOrEmpty(orderVo.getOrderId())){
			detail=new OrderDetail();
			detail.setId(IDUtil.serialId());
			detail.setCurPerAcc(account);
			String time=DateUtil.stubDateTime();
			detail.setBeginTime(time);
			detail.setBugetStatus(Config.BUGET_STATUS_NO_BUGET);
			detail.setFileStatus(Config.FILE_STATUS_NO_FILE);
			if(saveType.equals(Config.SAVE_TYPE_TEMP)){
				detail.setFinishTime("");
				detail.setCurStateId(Config.STATE_ZZJX);
			}else{
				detail.setFinishTime(time);
				detail.setCurStateId(Config.STATE_YJJS);
			}
			detail.setCurStageId(orderBo.getCurStageId());
			detail.setOrderBasic(orderBo);
			this.orderDao.save(detail);
		}else{
			OrderBasic bo=this.orderDao.getById(OrderBasic.class, orderBo.getOrderId());
			detail=bo.getDetailList().get(0);
			if(saveType.equals(Config.SAVE_TYPE_TEMP)){
				detail.setFinishTime("");
				detail.setCurStateId(Config.STATE_ZZJX);
			}else{
				detail.setFinishTime(DateUtil.stubDateTime());
				detail.setCurStateId(Config.STATE_YJJS);
			}
			detail.setCurStageId(orderBo.getCurStageId());
			detail.setOrderBasic(bo);
			this.orderDao.save(detail);
			this.orderDao.flush();
			this.orderDao.clear();
		}
		String resultValue="{'orderId':'"+orderBo.getOrderId()+"','ownerId':'"+ownerBo.getOwnerId()+"'}";
		System.out.println(resultValue);
		result=new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(),resultValue);
		return result;
	}
	/*
	 *ͨ���ɫ�����û�
	 */
	@Override
	public ResultMsg loadUsersByRole(String roleId) {
		ResultMsg result;
		String hql="From UserInfo u where u.roleType=:roleId";
		Map<Serializable, Serializable> map=new HashMap<Serializable, Serializable>();
		map.put("roleId", roleId);
		List<UserInfo> list=this.orderDao.queryByHql(hql, map);
		if(list.size()==0){
			result=new ResultMsg(ResultStatusCode.RESULT_NULL.getCode(), ResultStatusCode.RESULT_NULL.getMsg(), null);
			return result;
		}
		HashMap<String, String> user=new HashMap<String, String>();
		for(UserInfo item:list){
			user.put(item.getAccount(), item.getName());
		}
		result=new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), user);
		return result;
	}
	/*
	 * ͨ��������ѯ�������б�
	 * 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public Pager<?> queryUsersByConditionWithPage(ProductVo vo) {
		Pager page=this.orderDao.queryUsersByConditionWithPage(vo);
		return page;
	}
	/*
	 * ͨ��orderId��ѯ��order����Ϣ
	 * 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public OrderBasic queryOrderBasicByOrderId(String orderId) {
		OrderBasic orderBasic=this.orderDao.getById(OrderBasic.class, orderId);
		return orderBasic;
	}
	/*
	 * ͨ��orderId��stageId��ѯ����ǰ�׶εĸ�����Ϣ
	 * 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public List<FileInfo> loadFilesByOrderId(String orderId,String stageId) {
		String hql="select f from FileInfo f where f.orderBasicId=:orderBasicId and f.stageId=:stageId";
		Map<Serializable, Serializable> map=new HashMap<Serializable, Serializable>();
		map.put("orderBasicId", orderId);
		map.put("stageId", stageId);
		List<FileInfo> files=this.orderDao.queryByHql(hql, map);
		return files;
	}
	/*
	 * ͨ��orderId��stageId��ѯ����ǰ�׶ε�Ԥ��
	 * 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Buget> loadBugetsByOrderId(String orderId,String stageId) {
		String hql="select b from Buget b where b.orderId=:orderId ";
		Map<Serializable, Serializable> map=new HashMap<Serializable, Serializable>();
		map.put("orderId", orderId);
		List<Buget> bugets=this.orderDao.queryByHql(hql, map);
		return bugets;
	}
	/*
	 * ͨ��ownerId��ѯ��ҵ����ϢownerInfo����Ϣ
	 * 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public OwnerInfo queryOwnerByOwnerId(String ownerId) {
		OwnerInfo ownerInfo=this.orderDao.getById(OwnerInfo.class, ownerId);
		return ownerInfo;
	}
	/*
	 * ͨaccount��ѯ�û���Ϣ
	 * 
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public UserInfo queryUserByAccount(String account) {
		String hql="select u.name From UserInfo u where u.account=:account";
		Map<Serializable, Serializable> map=new HashMap<Serializable, Serializable>();
		map.put("account", account);
		List<UserInfo> user=this.orderDao.queryByHql(hql, map);
		return user.get(0);
	}
	/*
	 * �û��ֵ�Ƚ����⣬ר�ż���
	 * 
	 */
	@Override
	@Cacheable(cacheName="userCache")
	public List<DicUser> loadUserDic() {
		String hql="select new com.pxf.first.frame.enty.dic.bo.DicUser(u.account,u.name) from UserInfo u where 1=1";
		List<DicUser> list=this.orderDao.queryByHql(hql, null);
		return list;
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public ResultMsg loadEmailByAccount(String account) {
		ResultMsg result;
		String hql="select u from UserInfo u where u.account=:account";
		Map<Serializable, Serializable> map=new HashMap<Serializable, Serializable>();
		map.put("account", account);
		List<UserInfo> user=this.orderDao.queryByHql(hql, map);
		if(user.size()==0){
			result=new ResultMsg(ResultStatusCode.SYSTEM_ERR.getCode(), ResultStatusCode.SYSTEM_ERR.getMsg(), user);
			return result;
		}
		result=new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), user.get(0).getEmail());
//		String email=user.get(0).getEmail();
//		Email e=new Email();
//		e.setSubject("����");
//		e.setContent("��������");
//		e.setToEmail(email);
//		List<Email> list =new ArrayList<Email>();
//		list.add(e);
//		EmailThread thread=new EmailThread();
//		thread.initEmailService();
//		thread.setEmailBody(list);
//		thread.sendEmail(new EmailListener() {
//			
//			@Override
//			public void callBack(String result) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		return result;
	}
	//ͨ��Ԥ��id��ѯԤ����Ϣ
	@Override
	public Buget queryByBugetId(String bugetId) {
		Buget buget=this.orderDao.getById(Buget.class, bugetId);
		return buget;
	}
	//��ӵ���Ԥ����Ϣ
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public ResultMsg addBugetItem(Buget buget) {
		ResultMsg result;
		if(StringUtils.isNullOrEmpty( buget.getBugetId())){
			buget.setBugetId(IDUtil.serialId());
		}
		this.orderDao.save(buget);
		String orderId=buget.getOrderId();
		String bugetStr=buget.getBuget();
		String flag=buget.getBugetFlag();
		OrderBasic order=this.orderDao.getById(OrderBasic.class, orderId);
		int bugetInt=Integer.parseInt(bugetStr);
		if(flag.equals("in")){
			String bugetStro=(order.getBuget()==null||order.getBuget()=="")?"0":order.getBuget();
			int bugetInto=Integer.parseInt(bugetStro);
			order.setBuget(bugetInt+bugetInto+"");
		}else{
			String bugetStro=(order.getRelBuget()==null||order.getRelBuget()=="")?"0":order.getRelBuget();
			int bugetInto=Integer.parseInt(bugetStro);
			order.setRelBuget(bugetInt+bugetInto+"");
		}
		
		this.orderDao.save(order);
		result=new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), buget);
		return result;
	}
	@Override
	
	public FileInfo queryByFileId(String fileId) {
		FileInfo file=this.orderDao.getById(FileInfo.class, fileId);
		return file;
	}
	//���渽����Ϣ
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public ResultMsg saveFile(FileInfo file) {
		ResultMsg result;
		String hql="from FileInfo f where f.fileRelName=:fileRelName and f.stageId=:stageId and f.orderBasicId=:orderBasicId";
		Map<Serializable, Serializable> map=new HashMap<Serializable, Serializable>();
		map.put("fileRelName", file.getFileRelName());
		map.put("stageId", file.getStageId());
		map.put("orderBasicId", file.getOrderBasicId());
		List<FileInfo> files=this.orderDao.queryByHql(hql, map);
		if(files!=null&&files.size()>0&&StringUtils.isNullOrEmpty( file.getFileId())){
			result=new ResultMsg(ResultStatusCode.FILE_HAS_EXSIT.getCode(), ResultStatusCode.FILE_HAS_EXSIT.getMsg(), file);
			return result;
		}
		if(StringUtils.isNullOrEmpty( file.getFileId())){
			file.setFileId(IDUtil.serialId());
		}
		this.orderDao.save(file);
		result=new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), file);
		return result;
	}
	//ɾ�����Ϣ
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public ResultMsg deleteFile(String fileId) {
		ResultMsg result;
		String hql="delete from FileInfo f where f.fileId=:fileId";
		Map<Serializable, Serializable> map=new HashMap<Serializable, Serializable>();
		map.put("fileId", fileId);
		boolean succ=this.orderDao.excuteHqlByCondition(hql, map);
		if(succ){
			result=new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), null);
		}else{
			result=new ResultMsg(ResultStatusCode.SYSTEM_ERR.getCode(), ResultStatusCode.SYSTEM_ERR.getMsg(), null);
		}
		return result;
	}
	//ɾ��Ԥ����Ϣ
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public ResultMsg deleteBuget(String bugetId) {
		ResultMsg result;
		Buget buget=this.orderDao.getById(Buget.class, bugetId);
		String orderId=buget.getOrderId();
		String bugetStr=buget.getBuget();
		OrderBasic order=this.orderDao.getById(OrderBasic.class, orderId);
		String bugetStro=(order.getRelBuget()==null||order.getRelBuget()=="")?"0":order.getRelBuget();
		int bugetInt=Integer.parseInt(bugetStr);
		int bugetInto=Integer.parseInt(bugetStro);
		order.setRelBuget(bugetInt+bugetInto+"");
		this.orderDao.save(order);
		String hql="delete from Buget f where f.bugetId=:bugetId";
		Map<Serializable, Serializable> map=new HashMap<Serializable, Serializable>();
		map.put("bugetId", bugetId);
		boolean succ=this.orderDao.excuteHqlByCondition(hql, map);
		if(succ){
			result=new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), null);
		}else{
			result=new ResultMsg(ResultStatusCode.SYSTEM_ERR.getCode(), ResultStatusCode.SYSTEM_ERR.getMsg(), null);
		}
		return result;
	}
	//�޸Ľ׶���Ϣ
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public ResultMsg editStageInfo(OrderVo order, String detailId) {
		ResultMsg result;
		String orderId=order.getOrderId();
		OrderBasic bo=this.orderDao.getById(OrderBasic.class, orderId);
		//˵���޸ĵ��Ƕ�������׶εĻ���Ϣ
		if(StringUtils.isNullOrEmpty(detailId)){
			bo.setOrderTypeId(order.getOrderTypeId());
			bo.setOrderArea(order.getOrderArea());
			OwnerVo ownerVo=order.getOwner();
			this.orderDao.save(bo);
			OwnerInfo ownerBo=this.orderDao.getById(OwnerInfo.class, ownerVo.getOwnerId());
			ownerBo.setOwnerName(ownerVo.getOwnerName());
			ownerBo.setOwnerTypeId(ownerVo.getOwnerTypeId());
			ownerBo.setOwnerSex(ownerVo.getOwnerSex());
			ownerBo.setOwnerAddr(ownerVo.getOwnerAddr());
			ownerBo.setOwnerPhone(ownerVo.getOwnerPhone());
			ownerBo.setOwnerRemark(ownerVo.getOwnerRemark());
			this.orderDao.save(ownerBo);
			result=new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), null);
			return result;
			
		}else{
			OrderDetail detail=null;
			if(bo!=null){
				List<OrderDetail> details=bo.getDetailList();
				for(OrderDetail d:details){
					if(d.getId().equals(detailId)){
						detail=d;
						break;
					}
				}
				detail.setRemark(order.getRemark());
				detail.setOrderBasic(bo);
				this.orderDao.save(detail);
				result=new ResultMsg(ResultStatusCode.OK.getCode(), ResultStatusCode.OK.getMsg(), null);
				return result;
			}
		}
		return null;
	}
	

}
