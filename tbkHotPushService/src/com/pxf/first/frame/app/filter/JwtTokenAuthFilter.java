package com.pxf.first.frame.app.filter;

import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pxf.first.frame.app.result.model.ResultMsg;
import com.pxf.first.frame.app.result.model.ResultStatusCode;
import com.pxf.first.frame.app.security.jwt.auth.Audience;
import com.pxf.first.frame.app.security.jwt.auth.JwtHelper;

public class JwtTokenAuthFilter implements Filter {

	@Autowired  
    private Audience audienceEntity;
    private String[] excludesAction;
    private List<String> excludesUrllist;
	@Override
	public void destroy() {

	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request; 
		HttpServletResponse httpResponse = (HttpServletResponse) response;    
	    httpResponse.setCharacterEncoding("UTF-8");  
//	    httpResponse.setContentType("application/json; charset=utf-8");
		String action=httpRequest.getRequestURI();
		action=action.substring(action.lastIndexOf("/")+1);
		if(excludesUrllist.contains(action)){
			chain.doFilter(request, response);  
            return;  
		}
		ResultMsg resultMsg;  
        String token = (String) httpRequest.getParameter("token");  
		if (token != null) {
			Claims claims = JwtHelper.parseJWT(token,audienceEntity.getBase64Secret());
			if (claims != null) {
				ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(httpRequest);
				String userId = (String) claims.get("userid");
				String authStr = (String) claims.get("authStr");
				String account = (String) claims.get("myAccount");
				requestWrapper.addParameter("authStr", authStr);
				requestWrapper.addParameter("userId", userId);
				requestWrapper.addParameter("myAccount", account);
				chain.doFilter(requestWrapper, response);
				return;
			}
		}
          
      
     
  
        ObjectMapper mapper = new ObjectMapper();  
          
        resultMsg = new ResultMsg(ResultStatusCode.INVALID_TOKEN.getCode(), ResultStatusCode.INVALID_TOKEN.getMsg(), null);  
        httpResponse.getWriter().write(mapper.writeValueAsString(resultMsg));  
        return;

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		excludesAction=config.getInitParameter("excludes").split(",");
		excludesUrllist=new ArrayList<String>();
		for (int i = 0; i < excludesAction.length; i++) {
			excludesUrllist.add(excludesAction[i]);
		}
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,  
				config.getServletContext());
	}

}
