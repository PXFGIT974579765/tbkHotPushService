package com.pxf.first.frame.app.thread;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.pxf.first.frame.app.listener.EmailListener;
import com.pxf.first.frame.app.system.Config;
import com.pxf.first.frame.app.utils.PropertyUtil;
import com.pxf.first.frame.enty.email.vo.Email;
import com.pxf.first.frame.enty.email.vo.MyAuthenticator;
import com.sun.mail.util.MailSSLSocketFactory;

public class EmailThread extends Thread{
	private Properties props;
	private Session session;
	private Message msg;
	private EmailListener listener;
	private List<Email> list;
	private int count=0;

	@Override
	public void run() {
		try {
			if(list!=null){
				for(Email email:list){
					msg=creatMessage(email);
					Transport transport=session.getTransport();
					String fromEmail=props.getProperty("fromEmail");
					String password=props.getProperty("password");
//					transport.connect("smtp.mxhichina.com",fromEmail,password);
//					transport.sendMessage(msg, msg.getAllRecipients());
//					transport.close();
					Transport.send(msg);   
					count++;
					System.out.println("发送成功"+count);
				}
			}
			listener.callBack("success");
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			listener.callBack("fail");
		} catch (MessagingException e) {
			e.printStackTrace();
			listener.callBack("fail");
		}

	}

	//初始化邮件服务
	public void initEmailService() {
		MailSSLSocketFactory sf;
		props=PropertyUtil.getProperties(Config.PRO_FILE_EMAIL);
		//			sf = new MailSSLSocketFactory();
		//			sf.setTrustAllHosts(true);
		//			props.put("mail.smtp.ssl.socketFactory", sf);
					//获得邮件会话对象
				    MyAuthenticator auth=new MyAuthenticator(props.getProperty("fromEmail"), props.getProperty("password"));
					this.session=Session.getDefaultInstance(props,auth);
					this.msg=new MimeMessage(session);
		
	}
	//设置邮件主体

	public void setEmailBody(List<Email> emails) {
		this.list=emails;
	}

	public void sendEmail(EmailListener callBack) {
		listener=callBack;
		this.start();
	}
	public Message creatMessage(Email email){
		MimeMessage message = new MimeMessage(session);
		try {
			//发件人
			message.setFrom(new InternetAddress(props.getProperty("fromEmail")));
			//收件人
//			message.setRecipient(Message.RecipientType.TO, new InternetAddress(email.getToEmail()));
			message.addRecipients(Message.RecipientType.TO, list.get(0).getToInterAddr());
			//主题
			message.setSubject(email.getSubject());
			message.setSentDate(new Date()); 
			//内容
			message.setContent(email.getContent(), "text/html;charset=UTF-8");
			message.saveChanges();
			return message; 
		} catch (AddressException e) {
			e.printStackTrace();
			return null;
		} catch (MessagingException e) {
			e.printStackTrace();
			return null;
		}
	}
	

}
