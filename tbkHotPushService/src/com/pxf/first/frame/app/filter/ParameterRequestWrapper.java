package com.pxf.first.frame.app.filter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ParameterRequestWrapper extends HttpServletRequestWrapper {
	private Map<String , String[]> params = new HashMap<String, String[]>();  
	  
    @SuppressWarnings("unchecked")  
    public ParameterRequestWrapper(HttpServletRequest request) {  
        // ��request�������࣬�Ա��ڵ��ö�Ӧ������ʱ�򣬽����������ʵ�������ʵ�ַ�ʽ�͵�һ��new�ķ�ʽ����  
        super(request);  
        //���������������ǰ��Map�Ա��ڳ���request�еĲ���  
        this.params.putAll(request.getParameterMap());  
        this.modifyParameterValues();  
    }  
    //����һ�����췽��  
    public ParameterRequestWrapper(HttpServletRequest request , Map<String , Object> extendParams) {  
        this(request);  
        addAllParameters(extendParams);//���ｫ��չ����д�������  
    }  
  //��parameter��ֵȥ���ո����д��ȥ  
    public void modifyParameterValues(){ 
        Set<String> set =params.keySet();  
        Iterator<String> it=set.iterator();  
        while(it.hasNext()){  
           String key= (String) it.next();  
           String[] values = params.get(key);  
           values[0] = values[0].trim();  
           params.put(key, values);  
         }  
    }  
  //��дgetParameter����������ӵ�ǰ���е�map��ȡ   
    @Override  
    public String getParameter(String name) { 
        String[]values = params.get(name);  
        if(values == null || values.length == 0) {  
            return null;  
        }  
        return values[0];  
    }  
  //ͬ��  
    public String[] getParameterValues(String name) { 
         return params.get(name);  
    }  
  //���Ӷ������ 
   public void addAllParameters(Map<String , Object>otherParams) { 
        for(Map.Entry<String , Object>entry : otherParams.entrySet()) {  
            addParameter(entry.getKey() , entry.getValue());  
        }  
    }  
  
 //���Ӳ���
    public void addParameter(String name , Object value) {  
        if(value != null) {  
            if(value instanceof String[]) {  
                params.put(name , (String[])value);  
            }else if(value instanceof String) {  
                params.put(name , new String[] {(String)value});  
            }else {  
                params.put(name , new String[] {String.valueOf(value)});  
            }  
        }  
    }
    public void removeParameter(String name){
    	if(params.get(name)!=null){
    		params.remove(name);
    	}
    }

}
