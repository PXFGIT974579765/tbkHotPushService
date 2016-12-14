package com.pxf.first.frame.enty.menu.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_menu")
public class TreeNode implements Serializable {
	@Id
	@Column(name = "ID", nullable = false, insertable = true, updatable = true, length = 3)
	private String id;
	@Column(name = "PID", length = 20, nullable = true)
	private String pid;
	@Column(name = "TEXT", length = 20, nullable = true)
	private String text;
	@Column(name = "URL", length = 20, nullable = true)
	private String url;
	@Column(name = "IMG_URL", length = 20, nullable = true)
	private String imgUrl;
	@Column(name = "STATE", length = 20, nullable = true)
	private String state;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	

}
