package com.pxf.first.frame.app.security.jwt.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component("audienceEntity")
public class Audience {
	@Value("#{audience['clientId']}")
	private String clientId;  
	@Value("#{audience['base64Secret']}")
    private String base64Secret; 
	@Value("#{audience['name']}")
    private String name;  
	@Value("#{audience['expiresSecond']}")
    private int expiresSecond;  
    public String getClientId() {  
        return clientId;  
    }  
    public String getBase64Secret() {  
        return base64Secret;  
    }  
    public String getName() {  
        return name;  
    }  
    public int getExpiresSecond() {  
        return expiresSecond;  
    }  

}
