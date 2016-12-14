package com.pxf.first.frame.enty.product.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_owner")
public class OwnerInfo {
	@Id
	@Column(name = "owner_id", nullable = false, insertable = true, updatable = true, length = 32)
	private String ownerId;
	@Column(name = "owner_name",length=20,nullable=false)
	private String ownerName;
	@Column(name = "owner_phone",length=11,nullable=false)
	private String ownerPhone;
	@Column(name = "owner_addr",length=100,nullable=false)
	private String ownerAddr;
	@Column(name = "owner_sex",length=4,nullable=false)
	private String ownerSex;
	@Column(name = "owner_remark",length=200,nullable=false)
	private String ownerRemark;
	@Column(name = "owner_type_id",length=5,nullable=false)
	private String ownerTypeId;
	public String getOwnerTypeId() {
		return ownerTypeId;
	}
	public void setOwnerTypeId(String ownerTypeId) {
		this.ownerTypeId = ownerTypeId;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerPhone() {
		return ownerPhone;
	}
	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}
	public String getOwnerAddr() {
		return ownerAddr;
	}
	public void setOwnerAddr(String ownerAddr) {
		this.ownerAddr = ownerAddr;
	}
	public String getOwnerSex() {
		return ownerSex;
	}
	public void setOwnerSex(String ownerSex) {
		this.ownerSex = ownerSex;
	}
	public String getOwnerRemark() {
		return ownerRemark;
	}
	public void setOwnerRemark(String ownerRemark) {
		this.ownerRemark = ownerRemark;
	}

}
