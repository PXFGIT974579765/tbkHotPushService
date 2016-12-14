package com.pxf.first.frame.enty.product.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_buget")
public class OrderPerson {
	@Id
	@Column(name = "buget_id", nullable = false, insertable = true, updatable = true, length = 32)
	private String id;
	@Column(name = "person_id",length=32,nullable=false)
	private String personId;
	@Column(name = "order_id",length=32,nullable=false)
	private String orderId;
	@Column(name = "stage_id",length=5,nullable=false)
	private String stageId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStageId() {
		return stageId;
	}
	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

}
