package com.iss.project.checkin.model;

public class LambdaResponse {

	private int code;

	private String msg;

	public LambdaResponse() {
		super();
	}

	public LambdaResponse(int code, String msg) {
		this.code = code;
		this.msg = msg;
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
}
