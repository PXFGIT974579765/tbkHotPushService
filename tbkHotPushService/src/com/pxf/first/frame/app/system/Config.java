package com.pxf.first.frame.app.system;

public class Config {
	//订单发起阶段
	public static final String STAGE_DDFQ="000";
	//邀约客户阶段
	public static final String STAGE_YYKH="001";
	//量房阶段
	public static final String STAGE_LF="002";
	//设计方案和预算阶段
	public static final String STAGE_SJFA_YS="003";
	//洽谈方案和预算阶段
	public static final String STAGE_QTFA_YS="004";
	//确定方案阶段
	public static final String STAGE_QDFA="005";
	//选购材料阶段
	public static final String STAGE_XGCL="006";
	//施工阶段
	public static final String STAGE_SG="007";
	//交尾款阶段
	public static final String STAGE_JWK="008";
	//竣工阶段
	public static final String STAGE_JG="111";
	//正在进行状态
	public static final String STATE_ZZJX="0";
	//已经结束状态
	public static final String STATE_YJJS="1";
	//暂存
	public static final String SAVE_TYPE_TEMP="temp-save";
	//发送
	public static final String SAVE_TYPE_SEND="send-save";
	//有预算
	public static final String BUGET_STATUS_NO_BUGET="0";
	//有预算
	public static final String BUGET_STATUS_HAS_BUGET="1";
	//有文件
	public static final String FILE_STATUS_NO_FILE="0";
	//没文件
	public static final String FILE_STATUS_HAS_FILE="1";
	
	public static final String PRO_FILE_SYSTEM="/system.properties";
	public static final String PRO_FILE_EMAIL="/email.properties";
	public static final String FILE_SAVE_PATH="file_save_path";
	

}
