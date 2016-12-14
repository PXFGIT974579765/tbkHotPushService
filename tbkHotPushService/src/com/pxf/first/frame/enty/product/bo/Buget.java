package com.pxf.first.frame.enty.product.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_buget")
public class Buget {
	@Id
	@Column(name = "buget_id", nullable = false, insertable = true, updatable = true, length = 32)
	private String bugetId;
	@Column(name = "order_id",length=32,nullable=false)
	private String orderId;
	@Column(name = "buget_desc",length=30,nullable=true)
	private String bugetDesc;
	@Column(name = "buget",length=10,nullable=true)
	private String buget;
	@Column(name = "buget_time",length=10,nullable=true)
	private String bugetTime;
	@Column(name = "buget_flag",length=20,nullable=true)
	private String bugetFlag;
	@Column(name = "person_name",length=20,nullable=true)
	private String personName;
	@Column(name = "stage_id",length=20,nullable=true)
	private String stageId;
	@Column(name = "buget_per_acc",length=32,nullable=true)
	private String bugetPerAcc;
	public String getBugetPerAcc() {
		return bugetPerAcc;
	}
	public void setBugetPerAcc(String bugetPerAcc) {
		this.bugetPerAcc = bugetPerAcc;
	}
	public String getStageId() {
		return stageId;
	}
	public void setStageId(String stageId) {
		this.stageId = stageId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getBugetId() {
		return bugetId;
	}
	public void setBugetId(String bugetId) {
		this.bugetId = bugetId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getBugetDesc() {
		return bugetDesc;
	}
	public void setBugetDesc(String bugetDesc) {
		this.bugetDesc = bugetDesc;
	}
	public String getBugetTime() {
		return bugetTime;
	}
	public void setBugetTime(String bugetTime) {
		this.bugetTime = bugetTime;
	}
	public String getBugetFlag() {
		return bugetFlag;
	}
	public void setBugetFlag(String bugetFlag) {
		this.bugetFlag = bugetFlag;
	}
	public String getBuget() {
		return buget;
	}
	public void setBuget(String buget) {
		this.buget = buget;
	}
	

}
