package com.mall.client;

public enum ErrorCode {
	
	//通用錯誤
	PARAMETER_ERR("1001001", "parameter error {0}"),
	SORT_COLUMN_ERR("1001002", "sort column error {0}"),
	SYSTEM_ERR("1001003", "system error plz check log"),
	
	//登入相關錯誤
	ACCOUNT_DUPLICATE("1001101", "this account is used"),
	ACCOUNT_OR_PWS_INCORRECT("1001102", "account or password incorrect"),
	ACCOUNT_BAN("1001102","this account has been deactivated"),
	USER_ID_ILLEGAL("1001103","userId is illegal"),
	TOKEN_EXPIRED("1001104","token expired"),
	
	//使用者相關錯誤
	USER_ID_NOT_FOUND("1001201","this userId not found"),
	USER_CANT_BUY_PRODUCT("1001202","this user no right to buy product"),
	
	//商品相關錯誤
	PRODUCT_NOT_FOUND("1001301","this product not found"),
	BUY_FAIL("1001302","product not aviable or amount not enough"),
	BUY_SYSTEM_BUSY("1001303","buy system is busy , plz try again later");
	
	
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
