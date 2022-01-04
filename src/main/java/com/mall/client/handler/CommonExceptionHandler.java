package com.mall.client.handler;

import org.springframework.validation.BindingResult;
import com.mall.client.ErrorCode;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;
import java.util.List;
import org.springframework.http.HttpStatus;

import com.mall.client.dto.ActionResult;

@ControllerAdvice
public class CommonExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ActionResult handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		
		BindingResult exResult = ex.getBindingResult();
		List<FieldError> errorList = exResult.getFieldErrors();
		
		//將參數的錯誤訊息組合起來
		String parameterError = "";
		for(FieldError error : errorList) {
			parameterError = parameterError+error.getField()+":"+error.getDefaultMessage();
		}
		
		return new ActionResult(false, 
			ErrorCode.PARAMETER_ERR.getCode(), 
			MessageFormat.format(ErrorCode.PARAMETER_ERR.getMsg(),parameterError));

	}
}
