package com.pxf.first.frame.enty.dic.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dic_owner_type")
public class DicOwnerType {
	@Id
	 @Column(name = "owner_type_id", nullable = false, insertable = true, updatable = true, length = 5)
	private String ownerTypeId;
	 @Column(name = "owner_type_name",length=20,nullable=true)
	private String ownerTypeName;
	public String getOwnerTypeId() {
		return ownerTypeId;
	}
	public void setOwnerTypeId(String ownerTypeId) {
		this.ownerTypeId = ownerTypeId;
	}
	public String getOwnerTypeName() {
		return ownerTypeName;
	}
	public void setOwnerTypeName(String ownerTypeName) {
		this.ownerTypeName = ownerTypeName;
	}

}
