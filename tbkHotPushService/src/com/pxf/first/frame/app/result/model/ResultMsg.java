package com.pxf.first.frame.app.result.model;

public class ResultMsg {
	private int code;
	private String msg;
	private String token;
	private String resultValue;
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	private Object p2pdata;

	public ResultMsg(int ErrCode, String ErrMsg, Object P2pData) {
		this.code = ErrCode;
		this.msg = ErrMsg;
		this.p2pdata = P2pData;
	}
	public ResultMsg(int ErrCode, String ErrMsg, String value) {
		this.code = ErrCode;
		this.msg = ErrMsg;
		this.resultValue = value;
	}
	public String getResultValue() {
		return resultValue;
	}

	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}

	public ResultMsg(int ErrCode, String ErrMsg,String token, Object P2pData) {
		this.code = ErrCode;
		this.msg = ErrMsg;
		this.token=token;
		this.p2pdata = P2pData;
	}

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

	public Object getP2pdata() {
		return p2pdata;
	}

	public void setP2pdata(Object p2pdata) {
		this.p2pdata = p2pdata;
	}

}
