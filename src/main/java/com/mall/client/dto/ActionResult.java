package com.mall.client.dto;



import lombok.Data;

@Data
public class ActionResult {
	
	boolean success ;
	Object data;
	String errorCode;
	String errorMsg;
	
	public ActionResult(boolean success){
		this.success = success;
	}
	
	public ActionResult(boolean success , Object data){
		this.success = success;
		this.data = data;
	}
	
	public ActionResult(boolean success , String errorCode , String errorMsg){
		this.success = success;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
}
