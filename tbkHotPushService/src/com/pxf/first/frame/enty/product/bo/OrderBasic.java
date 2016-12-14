package com.pxf.first.frame.enty.product.bo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_order_basic")
public class OrderBasic implements Serializable{
	@Id
	@Column(name = "order_id", nullable = false, insertable = true, updatable = true, length = 32)
	private String orderId;
	@Column(name = "order_name", length = 50, nullable = true)
	private String orderName;
	@Column(name = "order_owner_id", length = 32, nullable = true)
	private String orderOwnerId;
	@Column(name = "start_time", length = 25, nullable = true)
	private String startTime;
	@Column(name = "finish_time", length = 25, nullable = true)
	private String finishTime;
	@Column(name = "establish_person_account", length = 32, nullable = true)
	private String estaPerAcc;
	@Column(name = "order_type_id", length = 5, nullable = true)
	private String orderTypeId;
	//�������ƽ��
	@Column(name = "order_area", length = 10, nullable = true)
    private String orderArea;
	@Column(name = "cur_state_id", length = 5, nullable = true)
	private String curStateId;
	@Column(name = "cur_stage_id", length = 5, nullable = true)
	private String curStageId;
	@Column(name = "next_state_id", length = 5, nullable = true)
	private String nextStateId;
	@Column(name = "next_stage_id", length = 5, nullable = true)
	private String nextStageId;
	@Column(name = "cur_person_account", length = 32, nullable = true)
	private String curPerAcc;
	@Column(name = "next_person_account", length = 32, nullable = true)
	private String nextPerAcc;


	@Column(name = "buget", length = 5, nullable = true)
	private String buget;
	@Column(name = "rel_buget", length = 5, nullable = true)
	private String relBuget;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "orderBasic")
	private List<OrderDetail> detailList;
	public String getBuget() {
		return buget;
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
	public void setCurPerAcc(String curPerAcc) {
		this.curPerAcc = curPerAcc;
	}
	public String getNextPerAcc() {
		return nextPerAcc;
	}
	public void setNextPerAcc(String nextPerAcc) {
		this.nextPerAcc = nextPerAcc;
	}

	public String getNextStageId() {
		return nextStageId;
	}

	public void setNextStageId(String nextStageId) {
		this.nextStageId = nextStageId;
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

	public String getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(String orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	public String getOrderArea() {
		return orderArea;
	}

	public void setOrderArea(String orderArea) {
		this.orderArea = orderArea;
	}

	

	public List<OrderDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<OrderDetail> detailList) {
		this.detailList = detailList;
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

	public String getEstaPerAcc() {
		return estaPerAcc;
	}

	public void setEstaPerAcc(String estaPerAcc) {
		this.estaPerAcc = estaPerAcc;
	}

}
