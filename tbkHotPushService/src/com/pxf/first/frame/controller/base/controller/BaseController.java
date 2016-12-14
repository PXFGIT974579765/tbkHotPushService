package com.pxf.first.frame.controller.base.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseController {
	
    public ObjectMapper mapper = new ObjectMapper();  
	
	public <T> void writeObjAsJsonObjToClient(HttpServletResponse response,T obj) throws JsonGenerationException, JsonMappingException, IOException{
		System.out.println(mapper.writeValueAsString(obj));
		response.getWriter().write(mapper.writeValueAsString(obj));
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String, String> getMapOfObj(List<?> objs){
		Map<String, String> map=new HashMap<String, String>();
		Class c;  
		String k = null;
		String v = null;
		for(Object obj:objs){
			try  
			{  
				c = Class.forName(obj.getClass().getName());  
				Method[] m = c.getDeclaredMethods();  
				for (int i = 0; i < m.length; i++)  
				{  
					String method = m[i].getName();  
					if (method.startsWith("get"))  
					{  
						if(method.endsWith("d")||method.endsWith("t")){
							k = (String)m[i].invoke(obj);  
						}else{
							v=(String)m[i].invoke(obj); 
						}
					}  
				}  
				map.put((String)k, (String)v);
			}  
			catch (Exception e)  
			{  
				e.printStackTrace();  
			}
		}
		return map;
		
	}
	

}
