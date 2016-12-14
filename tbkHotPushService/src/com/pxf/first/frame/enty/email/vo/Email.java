package com.pxf.first.frame.enty.email.vo;

import javax.mail.internet.InternetAddress;

public class Email {
	private String toEmail;
	private String content;
	private String subject;
	private InternetAddress[] toInterAddr;
	public InternetAddress[] getToInterAddr() {
		return toInterAddr;
	}
	public void setToInterAddr(InternetAddress[] toInterAddr) {
		this.toInterAddr = toInterAddr;
	}
	public String getToEmail() {
		return toEmail;
	}
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

}
