package com.pxf.first.frame.enty.menu.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EasyUITree implements Serializable {
	private String menuid;
	private String icon;
	private String menuname;
	private List<EasyUITree> menus;
	private List<EasyUITree> child;
	public List<EasyUITree> getChild() {
		return child;
	}
	public void setChild(List<EasyUITree> child) {
		this.child = child;
	}
	private String url;
	private String pid;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public List<EasyUITree> getMenus() {
		return menus;
	}
	public void setMenus(List<EasyUITree> menus) {
		this.menus = menus;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	

}
