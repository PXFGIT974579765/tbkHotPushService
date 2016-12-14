package com.pxf.first.frame.app.utils;

import java.util.Random;
import java.util.UUID;

public class IDUtil {
	/*
	 * 生成32的id
	 */
	public static String uuidTo32length(){
		String uuid = UUID.randomUUID().toString();
		if(uuid.length()!=32){
			uuid = uuid.replace("-", "");
			if(uuid.length()>32){
				uuid.substring(uuid.length()-32, 32);
			}
		}
		return uuid;
	}
	
	/*
	 * 根据时间和六位随机数生成一个序列id
	 */
	public static String serialId(){
		String dateStr=DateUtil.serialDateTime();
		//生成六位随机整数
		String randomStr=String.valueOf(new Random().nextInt(999999));
		return dateStr+randomStr;
	}

}
