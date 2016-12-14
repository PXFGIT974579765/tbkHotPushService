package com.pxf.first.frame.enty.email.vo;

import javax.mail.*;   

public class MyAuthenticator extends Authenticator{   
    String userName=null;   
    String password=null;   
        
    public MyAuthenticator(){   
    }   
    public MyAuthenticator(String username, String password) {    
        this.userName = username;    
        this.password = password;    
    }    
    protected PasswordAuthentication getPasswordAuthentication(){   
        return new PasswordAuthentication(userName, password);   
    }
//    public static void main(String[] args){   
//        //这个类主要是设置邮件   
//     MailSenderInfo mailInfo = new MailSenderInfo();    
//     mailInfo.setMailServerHost("smtp.qq.com");//目前仅限于QQ邮箱发送邮件
//     mailInfo.setMailServerPort("25");
//     mailInfo.setValidate(true);    
//     mailInfo.setUserName("974579765@qq.com");//你邮箱账号
//     mailInfo.setPassword("emddausiyzpdbdjj");//你的邮箱密码    
//     mailInfo.setFromAddress("974579765@qq.com");//你的邮箱地址
//     mailInfo.setToAddress("974579765@qq.com");//对方邮箱地址
//     mailInfo.setSubject("我很希望你能收到");//邮件标题
//     mailInfo.setContent("恭喜你,你的账号获得我公司抽取的二等奖.三星笔记本电脑一台.加9999元现金!等我回来了去银行领取!");//邮件类容    
//        //这个类主要来发送邮件   
//     SimpleMailSender sms = new SimpleMailSender();   
//         sms.sendTextMail(mailInfo);//发送文体格式    
//         sms.sendHtmlMail(mailInfo);//发送html格式   
//   }  

}   