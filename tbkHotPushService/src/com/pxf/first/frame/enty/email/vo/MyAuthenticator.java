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
//        //�������Ҫ�������ʼ�   
//     MailSenderInfo mailInfo = new MailSenderInfo();    
//     mailInfo.setMailServerHost("smtp.qq.com");//Ŀǰ������QQ���䷢���ʼ�
//     mailInfo.setMailServerPort("25");
//     mailInfo.setValidate(true);    
//     mailInfo.setUserName("974579765@qq.com");//�������˺�
//     mailInfo.setPassword("emddausiyzpdbdjj");//�����������    
//     mailInfo.setFromAddress("974579765@qq.com");//��������ַ
//     mailInfo.setToAddress("974579765@qq.com");//�Է������ַ
//     mailInfo.setSubject("�Һ�ϣ�������յ�");//�ʼ�����
//     mailInfo.setContent("��ϲ��,����˺Ż���ҹ�˾��ȡ�Ķ��Ƚ�.���ǱʼǱ�����һ̨.��9999Ԫ�ֽ�!���һ�����ȥ������ȡ!");//�ʼ�����    
//        //�������Ҫ�������ʼ�   
//     SimpleMailSender sms = new SimpleMailSender();   
//         sms.sendTextMail(mailInfo);//���������ʽ    
//         sms.sendHtmlMail(mailInfo);//����html��ʽ   
//   }  

}   