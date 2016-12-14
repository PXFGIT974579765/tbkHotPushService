package com.pxf.first.frame.app.utils;

import java.util.Random;
import java.util.UUID;

public class IDUtil {
	/*
	 * ����32��id
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
	 * ����ʱ�����λ���������һ������id
	 */
	public static String serialId(){
		String dateStr=DateUtil.serialDateTime();
		//������λ�������
		String randomStr=String.valueOf(new Random().nextInt(999999));
		return dateStr+randomStr;
	}

}
