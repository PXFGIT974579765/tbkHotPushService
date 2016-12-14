package com.pxf.first.frame.enty.product.vo;

import com.pxf.first.frame.enty.product.bo.OrderBasic;

public class OrderDetailVo {
	private String id;
	private String curStageId;
	private String curStateId;
	private String curPerAcc;
	private String fileStatus;
	private String beginTime;
	private String finishTime;
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCurStageId() {
		return curStageId;
	}
	public void setCurStageId(String curStageId) {
		this.curStageId = curStageId;
	}
	public String getCurStateId() {
		return curStateId;
	}
	public void setCurStateId(String curStateId) {
		this.curStateId = curStateId;
	}
	public String getCurPerAcc() {
		return curPerAcc;
	}
	public void setCurPerAcc(String curPerAcc) {
		this.curPerAcc = curPerAcc;
	}
	public String getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getBugetStatus() {
		return bugetStatus;
	}
	public void setBugetStatus(String bugetStatus) {
		this.bugetStatus = bugetStatus;
	}
	public OrderBasic getOrderBasic() {
		return orderBasic;
	}
	public void setOrderBasic(OrderBasic orderBasic) {
		this.orderBasic = orderBasic;
	}
	private String bugetStatus;
	private OrderBasic orderBasic;

}
