package com.mall.client.dto;



import lombok.Data;

@Data
public class ActionResult {
	
	boolean sucess ;
	Object data;
	String errorCode;
	String errorMsg;
	
	public ActionResult(boolean sucess){
		this.sucess = sucess;
	}
	
	public ActionResult(boolean sucess , Object data){
		this.sucess = sucess;
		this.data = data;
	}
	
	public ActionResult(boolean sucess , String errorCode , String errorMsg){
		this.sucess = sucess;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
}
