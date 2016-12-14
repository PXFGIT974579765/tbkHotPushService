package com.pxf.first.frame.app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class PropertyUtil {
	private static final Logger LOG = Logger.getLogger(PropertyUtil.class);
	private static PropertyUtil instance = new PropertyUtil();
	private static List<String> fileList=new ArrayList<String>();

	private static Map<String, String> propertiesMap = new HashMap<String, String>();
	private PropertyUtil(){
		
	}
	//proFile为要加载的配置文件名
	public static PropertyUtil getInstance(String proFile){
		if(!fileList.contains(proFile)){
			instance.read(proFile);
			fileList.add(proFile);
		}
		return instance;
	}
	public synchronized void loadProperties(String proFile) {
		if(!fileList.contains(proFile)){
			this.read(proFile);
			fileList.add(proFile);
		}
	}
	 private void read(String conf) {
	        if (StringUtils.isNotBlank(conf)) {
	            InputStream is = null;
	            Properties props = new Properties();

	            try {
	                is = PropertyUtil.class.getResourceAsStream(conf);
	                BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
	                props.load(br);
	                Enumeration<?> propKeys = props.propertyNames();
	                while (propKeys.hasMoreElements()) {
	                    String propName = (String) propKeys.nextElement();
	                    String propValue = props.getProperty(propName);

	                    propertiesMap.put(propName, propValue);
	                    LOG.info("Load system.properties : " + propName + " = " + propValue);
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	                LOG.info("Load app.properties failed.");
	            } finally {
	                if (is != null) {
	                    try {
	                        is.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                        LOG.info("Load app.properties failed.");
	                    }
	                }
	            }
	        }
	    }

	public static String getProperty(String propertyName) {
		return (String) propertiesMap.get(propertyName);
	}
	public static Properties getProperties(String proFile){
		Properties props = new Properties();
		 if (StringUtils.isNotBlank(proFile)) {
	            InputStream is = null;
	            try {
	                is = PropertyUtil.class.getResourceAsStream(proFile);
	                BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
	                props.load(br);
	                return props;
	                		
	            } catch (IOException e) {
	                e.printStackTrace();
	                LOG.info("Load app.properties failed.");
	                return null;
	            } finally {
	                if (is != null) {
	                    try {
	                        is.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                        LOG.info("Load app.properties failed.");
	                    }
	                }
	            }
	        }
		 return null;
	}

}
