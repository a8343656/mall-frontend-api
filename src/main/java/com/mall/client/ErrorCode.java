package com.mall.client;

public enum ErrorCode {
	
	//通用錯誤
	PARAMETER_ERR("1001001", "parameter error {0}"),
	
	//登入相關錯誤duplicate
	ACCOUNT_DUPLICATE("1001101", "this account is used");
	
	private String code;
	private String msg;

	ErrorCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}



	public String getCode() {
		return this.code;
	}

	public String getMsg() {
		return this.msg;
	}
}
