package com.pxf.first.frame.app.result.model;

public enum ResultStatusCode {
	OK(0, "OK"),  
    SYSTEM_ERR(30001, "��¼��ʱ��ϵͳ����"),
	INVALID_LOGIN (30003, "�û������������"),  
	INVALID_CAPTCHA(30005, "Invalid captcha or captcha overdue"),  
	INVALID_TOKEN(30006, "�����֤ʧ��"),
	UPDATE_NEW_PWD(10002,"�������벻���"),
	UPDATE_PWD(10001,"ԭʼ���벻��ȷ"),
	USER_EXIST(20001,"�û��Ѵ���"),
	OWNER_ADD(4001,"�����ҵ����Ϣ�׶γ���"),
	RESULT_NULL(5001,"��ѯ���Ϊ��"),
	NO_SELECT_DATA_ITEM(7001,"û��ѡ��������"),
	FILE_HAS_BE_DELETED(6001,"�ļ��Ѿ���ɾ��"),
	FILE_HAS_EXSIT(6002,"�ļ��Ѵ���"),
	FILE_IS_PRIVATE(6003,"�ļ����Լ�����Ԥ��");
      
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
