package com.pxf.first.frame.enty.dic.bo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dic_role_type")
public class DicRoleType {
	@Id
	@Column(name = "role_id", nullable = false, insertable = true, updatable = true, length = 3)
	private String roleId;
	@Column(name = "role_name", length = 20, nullable = false)
	private String roleName;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
