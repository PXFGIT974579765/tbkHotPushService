package com.pxf.first.frame.enty.user.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 用户信息
 * @author Luxh
 */

@Entity
@Table(name="t_user_info")
public class UserInfo implements Serializable{

    private static final long serialVersionUID = -3838732995856086555L;
    
    @Id
    @Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 36)
    private String id;
    
    //账号
    @Column(name = "account",length=20,nullable=true)
    private String account;
    //密码
    @Column(name = "password",length=20,nullable=true)
    private String password;
    //姓名
    @Column(name = "name",length=32,nullable=true)
    private String name;
    @Column(name = "role_type",length=3,nullable=true,insertable=true, updatable=true)
    private String roleType;
    @Column(name = "phone",length=11,nullable=true)
    private String phone;
    @Column(name = "sex",length=4,nullable=true)
    private String sex;
    @Transient
    private String roleName;
    @Transient
    private String authStr;
    @Transient
    public static final String IS_VALID="1";
    @Transient
    public static final String NO_VALID="0";
    @Transient
    public static final String ALL_VALID="3";
    //是否可用，如果为1则可用，0不可用，即被删除了
    @Column(name = "isValid",length=3,nullable=false)
    private String isValid;
    public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getAuthStr() {
		return authStr;
	}
	public void setAuthStr(String authStr) {
		this.authStr = authStr;
	}
	@Column(name = "email",length=20,nullable=true)
    private String email;
    public UserInfo() {
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	
    
    public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	//getter、setter
    //......     
	public String  getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Transient
	public Object getPrimaryKey() {
		return id;
	}

}
