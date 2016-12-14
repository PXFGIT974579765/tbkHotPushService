package com.pxf.first.frame.enty.product.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_order_detail")
public class OrderDetail implements Serializable{
    @Id
	@Column(name = "id", nullable = false, insertable = true, updatable = true, length = 32)
	private String id;
    @Column(name = "cur_stage_id",length=5,nullable=true)
	private String curStageId;
	@Column(name = "cur_state_id",length=5,nullable=false)
    private String curStateId;
    @Column(name = "curren_person_account",length=32,nullable=true)
	private String curPerAcc;
    @Column(name = "file_status",length=32,nullable=true)
	private String fileStatus;
    @Column(name = "begin_time",length=32,nullable=false)
	private String beginTime;
    @Column(name = "finish_time",length=32,nullable=true)
	private String finishTime;
    @Column(name = "remark",length=100,nullable=true)
	private String remark;
    @Column(name = "buget_status",length=5,nullable=true)
	private String bugetStatus;
    @ManyToOne
	@JoinColumn(name = "order_basic_id")
    private OrderBasic orderBasic;
	public OrderBasic getOrderBasic() {
		return orderBasic;
	}
	public void setOrderBasic(OrderBasic orderBasic) {
		this.orderBasic = orderBasic;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCurPerAcc() {
		return curPerAcc;
	}
	public void setCurPerAcc(String curPerAcc) {
		this.curPerAcc = curPerAcc;
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
	public String getFileStatus() {
		return fileStatus;
	}
	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}
	public String getBugetStatus() {
		return bugetStatus;
	}
	public void setBugetStatus(String bugetStatus) {
		this.bugetStatus = bugetStatus;
	}
	

}
