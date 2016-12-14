package com.pxf.first.frame.enty.user.vo;

import java.util.List;

public class LoginParaVo {
	private String userName;
	private String pswd;
	private String newPwd;
	private String newPwd1;
	private String email;
	private String phone;
	private String sex;
	private String account;
	private String role;  
	private String Id;
	
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	/*
	 * 以下分页用===========
	 * 当前页
	 */
    private int currentPage;
	//每页条数
  	private int pageRows;
  	/*
  	 * 以上分页用===========
  	 */
    public int getCurrentPage() {
  		return currentPage;
  	}
  	public void setCurrentPage(int currentPage) {
  		this.currentPage = currentPage;
  	}
  	public int getPageRows() {
  		return pageRows;
  	}
  	public void setPageRows(int pageRows) {
  		this.pageRows = pageRows;
  	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	private List<LoginParaVo> users;
	public List<LoginParaVo> getUsers() {
		return users;
	}
	public void setUsers(List<LoginParaVo> users) {
		this.users = users;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getNewPwd1() {
		return newPwd1;
	}
	public void setNewPwd1(String newPwd1) {
		this.newPwd1 = newPwd1;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

}
