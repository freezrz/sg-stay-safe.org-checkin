package com.iss.project.checkin.model;

import com.iss.project.checkin.Constants;

public class SafeResponse {

	private int code;

	private String msg;
	private Object result;

	public SafeResponse() {
		super();
	}

	public SafeResponse(int code, String msg, Object result) {
		this.code = code;
		this.msg = msg;
		this.result = result;
	}

	public SafeResponse(String msg) {
		this.msg = msg;
	}

	public static SafeResponse responseSuccess(String msg){
		return new SafeResponse(Constants.RESPONSE_CODE_SUCCESS, msg, null);
	}

	public static SafeResponse responseSuccess(String msg, Object obj){
		return new SafeResponse(Constants.RESPONSE_CODE_SUCCESS, msg, obj);
	}

	public static SafeResponse responseFail(int code, String msg){
		return new SafeResponse(code, msg, null);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
