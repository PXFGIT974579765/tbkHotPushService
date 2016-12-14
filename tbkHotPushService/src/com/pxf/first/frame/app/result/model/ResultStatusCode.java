package com.pxf.first.frame.app.result.model;

public enum ResultStatusCode {
	OK(0, "OK"),  
    SYSTEM_ERR(30001, "登录超时或系统错误"),
	INVALID_LOGIN (30003, "用户名或密码错误"),  
	INVALID_CAPTCHA(30005, "Invalid captcha or captcha overdue"),  
	INVALID_TOKEN(30006, "身份验证失败"),
	UPDATE_NEW_PWD(10002,"两次密码不相等"),
	UPDATE_PWD(10001,"原始密码不正确"),
	USER_EXIST(20001,"用户已存在"),
	OWNER_ADD(4001,"在添加业主信息阶段出错"),
	RESULT_NULL(5001,"查询结果为空"),
	NO_SELECT_DATA_ITEM(7001,"没有选择数据项"),
	FILE_HAS_BE_DELETED(6001,"文件已经被删除"),
	FILE_HAS_EXSIT(6002,"文件已存在"),
	FILE_IS_PRIVATE(6003,"文件仅自己可以预览");
      
    private int code;  
    private String msg;  
    public int getCode() {  
        return code;  
    }  
  
    public void setCode(int errcode) {  
        this.code = errcode;  
    }  
  
    public String getMsg() {  
        return msg;  
    }  
  
    public void setMsg(String errmsg) {  
        this.msg = errmsg;  
    }  
    private ResultStatusCode(int Errode, String ErrMsg)  
    {  
        this.code = Errode;  
        this.msg = ErrMsg;  
    }

}
