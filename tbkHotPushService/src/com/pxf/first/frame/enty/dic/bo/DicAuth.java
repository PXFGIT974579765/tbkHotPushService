package com.pxf.first.frame.enty.dic.bo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="dic_auth")
public class DicAuth {
	 @Id
	 @Column(name = "role_id", nullable = false, insertable = true, updatable = true, length = 3)
	private String role_id;
	 @Column(name = "auth_str",length=50,nullable=true)
	private String authStr;
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getAuthStr() {
		return authStr;
	}
	public void setAuthStr(String authStr) {
		this.authStr = authStr;
	}
	@Transient
	public Object getPrimaryKey() {
		return role_id;
	}

}
