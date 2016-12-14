package com.pxf.first.frame.app.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import com.pxf.first.frame.app.listener.EmailListener;
import com.pxf.first.frame.app.system.Config;
import com.pxf.first.frame.app.thread.EmailThread;
import com.pxf.first.frame.enty.email.vo.Email;

public class SendEmail {

	public static void main(String[] args) {
		StringBuffer sb=new StringBuffer();
		PropertyUtil pro=PropertyUtil.getInstance(Config.PRO_FILE_SYSTEM);
		String qqtxt=PropertyUtil.getProperty("qqtxt") ;
		sb.append("<div id='mailContentContainer' class='qmbox qm_con_body_content qqmail_webmail_only' style=''><p>亲爱的群友，您好！</p>");
		sb.append("<p>&nbsp; 由于最近加群《淘宝特价淘宝天猫》群的人数越来越多，特开了老用户群，我们将在群里提供更优质的推荐，群号<span style='border-bottom:1px dashed #ccc;z-index:1' t='7' onclick='return false;' data='296681502'>296681502</span>。</p>");
		sb.append("<p>&nbsp; 微博号 二师兄挑白菜 <a href='http://weibo.com/u/6024816711?topnav=1&amp;wvr=6&amp;topsug=1&amp;is_all=1' target='_blank'>关注微博</a></p>");
		sb.append("<p>&nbsp; 微信公众号 优品热推</p>");
		sb.append("<p>&nbsp; 欢迎您的到来！</p>");
		sb.append("<p>&nbsp;</p><style type='text/css'>.qmbox style, .qmbox script, .qmbox head, .qmbox link, .qmbox meta {display: none !important;}</style></div>");
        File file =new File(qqtxt);
        if(!file.exists()){
        	System.out.println("文件不存在");
        	return;
        }
        System.out.println("开始读取qq文本信息==========");
       
        String line;  
        String reg="^[0-9]*$";
        List<String> ls=new ArrayList<String>();
    	List<Email> list =new ArrayList<Email>();
    	InputStream in = null;
    	 InputStreamReader isr = null;
    	 BufferedReader br = null;
        try {
        	in=new FileInputStream(file);
        	isr=new InputStreamReader(in);
        	br=new BufferedReader(isr);
			while((line=br.readLine()) != null){     
			    System.out.println(line); 
			    if(line.matches(reg)){
			    	line+="@qq.com";
			    	ls.add(line);
//			    	Email e=new Email();
//					e.setSubject("故友不见，且寻之");
//					e.setContent(sb.toString());
//					e.setToEmail(line.trim());
//					count++;
//					list.add(e);
					
			    }
			}
			Email e=new Email();
			e.setSubject("故友不见，且寻之");
			e.setContent(sb.toString());
			InternetAddress[] toMail = new InternetAddress[ls.size()];  
			for (int i = 0; i < ls.size(); i++) {
				try {
					toMail[i]=new InternetAddress(ls.get(i));
				} catch (AddressException e1) {
					e1.printStackTrace();
				}
			}
			e.setToInterAddr(toMail);
//			e.setToEmail(line.trim());
			list.add(e);
			System.out.println("发送总数"+ls.size());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
				isr.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	
		
		EmailThread thread=new EmailThread();
		thread.initEmailService();
		thread.setEmailBody(list);
		thread.sendEmail(new EmailListener() {
			
			@Override
			public void callBack(String result) {
				// TODO Auto-generated method stub
				
			}
		});

	}

}
