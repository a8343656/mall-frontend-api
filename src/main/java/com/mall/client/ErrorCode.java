package com.mall.client;

public enum ErrorCode {
	
	//通用錯誤
	PARAMETER_ERR("1001001", "parameter error {0}"),
	
	//登入相關錯誤
	ACCOUNT_DUPLICATE("1001101", "this account is used"),
	ACCOUNT_OR_PWS_INCORRECT("1001102", "account or password incorrect"),
	ACCOUNT_BAN("1001102","this account has been deactivated"),
	
	//使用者相關錯誤
	USER_ID_NOT_FOUND("1001201","this userId not found"),
	
	
	//商品相關錯誤
	PRODUCT_NOT_FOUND("1001301","this product not found");
	
	
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
