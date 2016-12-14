package com.pxf.first.frame.app.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
/*
 * 作者：彭雄峰
 * 时间：2016-10-25 23:24
 */
public class DateUtil {
	private static SimpleDateFormat formatter;
	
	/*
	 * 生成连续的时间字符串
	 */
	public static String serialDateTime(){
		formatter=new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(new Date());
	}
	//生成短横杠的时间字符串
	public static String stubDateTime(){
		formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(new Date());
	}
	//生成指定格式的时间字符串
	public static String dateTime(String pattern){
		formatter=new SimpleDateFormat(pattern);
		return formatter.format(new Date());
	}

}
