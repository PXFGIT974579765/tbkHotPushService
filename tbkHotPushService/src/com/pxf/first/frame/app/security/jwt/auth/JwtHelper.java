package com.pxf.first.frame.app.security.jwt.auth;
import java.security.Key;  
import java.util.Date;  
  
import javax.crypto.spec.SecretKeySpec;  
import javax.xml.bind.DatatypeConverter;  
  
import io.jsonwebtoken.Claims;  
import io.jsonwebtoken.JwtBuilder;  
import io.jsonwebtoken.Jwts;  
import io.jsonwebtoken.SignatureAlgorithm;  
  
public class JwtHelper {  
    public static Claims parseJWT(String jsonWebToken, String base64Security){  
        try  
        {  
            Claims claims = Jwts.parser()  
                       .setSigningKey(DatatypeConverter.parseBase64Binary(base64Security))  
                       .parseClaimsJws(jsonWebToken).getBody();  
            return claims;  
        }  
        catch(Exception ex)  
        {  
            return null;  
        }  
    }  
      
    public static String createJWT(String account, String userId, String authStr,
            String audience, String issuer, long TTLMillis, String base64Security)   
    {  
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;  
           
        long nowMillis = System.currentTimeMillis();  
        Date now = new Date(nowMillis);  
           
        //����ǩ����Կ  
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(base64Security);  
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());  
           
          //��ӹ���JWT�Ĳ���  
        JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")  
                                        .claim("myAccount", account)  
                                        .claim("userid", userId)
                                        .claim("authStr", authStr)
                                        .setIssuer(issuer)  
                                        .setAudience(audience)  
                                        .signWith(signatureAlgorithm, signingKey);  
         //���Token����ʱ��  
        if (TTLMillis >= 0) {  
            long expMillis = nowMillis + TTLMillis;  
            Date exp = new Date(expMillis);  
            builder.setExpiration(exp).setNotBefore(now);  
        }  
           
         //����JWT  
        return builder.compact();  
    }   
}
