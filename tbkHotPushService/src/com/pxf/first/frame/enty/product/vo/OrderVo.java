package com.pxf.first.frame.enty.product.vo;

import java.io.Serializable;
import java.util.List;



public class OrderVo implements Serializable {
	private String orderId;
	private String orderName;
	private String orderOwnerId;
	private String startTime;
	private String startTimeFrom;
	private String startTimeTo;
	private String finishTime;
	private String orderTypeId;
	private String orderTypeName;
	private String orderArea;
	private String orderAreaFrom;
	private String orderAreaTo;
	private OwnerVo owner;
	private String curStateId;
	private String curStateName;
	private String curStageId;
	private String curStageName;
	private String nextStateId;
	private String nextStateName;
	private String nextStageId;
	private String nextStageName;
	private String estaPerAcc;
	private String estaPerAccName;
	private String buget;
	private String nextPerName;
	private String remark;
	private String detailId;
	
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	private String relBuget;
	private String bugetFrom;
	private String bugetTo;
	private String isSelf;
	private String curPerAcc;
	private String nextPerAcc;
	private List<OrderDetailVo> detailList;
	
	//�������ڷ��㷵������ʹ��
	private String ownerName;
	private String ownerAddr;
	private String ownerPhone;
	//���Ͻ���
	/*
	 * ���·�ҳ��===========
	 * ��ǰҳ
	 */
    private int currentPage;
	//ÿҳ����
  	private int pageRows;
  	/*
  	 * ���Ϸ�ҳ��===========
  	 */
  	public OrderVo() {
		// TODO Auto-generated constructor stub
	}
  	//���ؽ��vo���캯��
  	public OrderVo(String orderId,String orderName,String nextPerAcc,String nextPerName,String curStateId,String curStageId,String estaPerAcc,String ownerName,String startTime,String finishTime,String ownerAddr
  			,String ownerPhone,String buget,String orderArea,String curStateName,String curStageName,String estaPerAccName,String orderTypeName,String nextStageId,String nextStageName) {
		this.orderId=orderId;
		this.orderName=orderName;
		this.nextPerAcc=nextPerAcc;
		this.curStateId=curStateId;
		this.curStageId=curStageId;
		this.estaPerAcc=estaPerAcc;
		this.nextPerName=nextPerName;
		this.ownerName=ownerName;
		this.startTime=startTime;
		this.finishTime=finishTime;
		this.ownerAddr=ownerAddr;
		this.ownerPhone=ownerPhone;
		this.buget=buget;
		this.orderArea=orderArea;
		this.curStateName=curStateName;
		this.curStageName=curStageName;
		this.estaPerAccName=estaPerAccName;
		this.orderTypeName=orderTypeName;
		this.nextStageId=nextStageId;
		this.nextStageName=nextStageName;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerAddr() {
		return ownerAddr;
	}
	public void setOwnerAddr(String ownerAddr) {
		this.ownerAddr = ownerAddr;
	}
	public String getOrderTypeName() {
		return orderTypeName;
	}
	public String getNextStateId() {
		return nextStateId;
	}
	public void setNextStateId(String nextStateId) {
		this.nextStateId = nextStateId;
	}
	public String getCurPerAcc() {
		return curPerAcc;
	}
	public String getNextPerName() {
		return nextPerName;
	}
	public void setNextPerName(String nextPerName) {
		this.nextPerName = nextPerName;
	}
	public void setCurPerAcc(String curPerAcc) {
		this.curPerAcc = curPerAcc;
	}
	public String getNextStateName() {
		return nextStateName;
	}
	public String getNextPerAcc() {
		return nextPerAcc;
	}
	public void setNextPerAcc(String nextPerAcc) {
		this.nextPerAcc = nextPerAcc;
	}
	public void setNextStateName(String nextStateName) {
		this.nextStateName = nextStateName;
	}
	public String getNextStageId() {
		return nextStageId;
	}
	public void setNextStageId(String nextStageId) {
		this.nextStageId = nextStageId;
	}
	public String getNextStageName() {
		return nextStageName;
	}
	public void setNextStageName(String nextStageName) {
		this.nextStageName = nextStageName;
	}
	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}
	public String getIsSelf() {
		return isSelf;
	}
	public void setIsSelf(String isSelf) {
		this.isSelf = isSelf;
	}
	public String getCurStateName() {
		return curStateName;
	}
	public void setCurStateName(String curStateName) {
		this.curStateName = curStateName;
	}
	public String getCurStageName() {
		return curStageName;
	}
	public void setCurStageName(String curStageName) {
		this.curStageName = curStageName;
	}
	public String getEstaPerAccName() {
		return estaPerAccName;
	}
	public void setEstaPerAccName(String estaPerAccName) {
		this.estaPerAccName = estaPerAccName;
	}
	public String getOwnerPhone() {
		return ownerPhone;
	}
	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}
	public String getStartTimeFrom() {
		return startTimeFrom;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageRows() {
		return pageRows;
	}
	public void setPageRows(int pageRows) {
		this.pageRows = pageRows;
	}
	public void setStartTimeFrom(String startTimeFrom) {
		this.startTimeFrom = startTimeFrom;
	}
	public String getStartTimeTo() {
		return startTimeTo;
	}
	public void setStartTimeTo(String startTimeTo) {
		this.startTimeTo = startTimeTo;
	}
	
	public String getBugetFrom() {
		return bugetFrom;
	}
	public void setBugetFrom(String bugetFrom) {
		this.bugetFrom = bugetFrom;
	}
	public String getBugetTo() {
		return bugetTo;
	}
	public void setBugetTo(String bugetTo) {
		this.bugetTo = bugetTo;
	}
	public String getBuget() {
		return buget;
	}
	public void setBuget(String buget) {
		this.buget = buget;
	}
	public String getRelBuget() {
		return relBuget;
	}
	public void setRelBuget(String relBuget) {
		this.relBuget = relBuget;
	}
	public String getCurStateId() {
		return curStateId;
	}
	public void setCurStateId(String curStateId) {
		this.curStateId = curStateId;
	}
	public String getCurStageId() {
		return curStageId;
	}
	public void setCurStageId(String curStageId) {
		this.curStageId = curStageId;
	}
	public String getOrderArea() {
		return orderArea;
	}
	public String getOrderAreaFrom() {
		return orderAreaFrom;
	}
	public void setOrderAreaFrom(String orderAreaFrom) {
		this.orderAreaFrom = orderAreaFrom;
	}
	public String getOrderAreaTo() {
		return orderAreaTo;
	}
	public void setOrderAreaTo(String orderAreaTo) {
		this.orderAreaTo = orderAreaTo;
	}
	public void setOrderArea(String orderArea) {
		this.orderArea = orderArea;
	}
	public String getOrderTypeId() {
		return orderTypeId;
	}
	public void setOrderTypeId(String orderTypeId) {
		this.orderTypeId = orderTypeId;
	}
	public OwnerVo getOwner() {
		return owner;
	}
	public void setOwner(OwnerVo owner) {
		this.owner = owner;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getOrderOwnerId() {
		return orderOwnerId;
	}
	public void setOrderOwnerId(String orderOwnerId) {
		this.orderOwnerId = orderOwnerId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public List<OrderDetailVo> getDetailList() {
		return detailList;
	}
	public void setDetailList(List<OrderDetailVo> detailList) {
		this.detailList = detailList;
	}
	public String getEstaPerAcc() {
		return estaPerAcc;
	}
	public void setEstaPerAcc(String estaPerAcc) {
		this.estaPerAcc = estaPerAcc;
	}
	

}
