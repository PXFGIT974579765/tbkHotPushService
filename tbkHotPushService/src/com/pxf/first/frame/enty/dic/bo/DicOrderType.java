package com.pxf.first.frame.enty.dic.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dic_order_type")
public class DicOrderType {
	@Id
	 @Column(name = "order_type_id", nullable = false, insertable = true, updatable = true, length = 5)
	private String orderTypeId;
	 @Column(name = "order_type_name",length=20,nullable=true)
	private String orderTypeName;
	public String getOrderTypeId() {
		return orderTypeId;
	}
	public void setOrderTypeId(String orderTypeId) {
		this.orderTypeId = orderTypeId;
	}
	public String getOrderTypeName() {
		return orderTypeName;
	}
	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

}
