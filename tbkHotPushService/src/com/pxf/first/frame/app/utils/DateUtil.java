package com.pxf.first.frame.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
/*
 * ���ߣ����۷�
 * ʱ�䣺2016-10-25 23:24
 */
public class DateUtil {
	private static SimpleDateFormat formatter;
	
	/*
	 * ����������ʱ���ַ���
	 */
	public static String serialDateTime(){
		formatter=new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(new Date());
	}
	//���ɶ̺�ܵ�ʱ���ַ���
	public static String stubDateTime(){
		formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(new Date());
	}
	//����ָ����ʽ��ʱ���ַ���
	public static String dateTime(String pattern){
		formatter=new SimpleDateFormat(pattern);
		return formatter.format(new Date());
	}

}
