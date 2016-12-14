package com.pxf.first.frame.enty.dic.bo;

public class DicUser {
	private String account;
	private String name;
	public DicUser(String account,String name) {
		this.account=account;
		this.name=name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
