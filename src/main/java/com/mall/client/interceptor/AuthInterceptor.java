package com.mall.client.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.client.ErrorCode;
import com.mall.client.dto.ActionResult;
import com.mall.client.entity.UserLoginData;
import com.mall.client.repository.UserLoginDataRepository;
import com.sun.istack.Nullable;

import lombok.var;

@Component
public class AuthInterceptor implements HandlerInterceptor{
	
	@Autowired UserLoginDataRepository userLoginDataRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	throws Exception {
		
		try {
			// preflight request 略過檢查
			if(request.getMethod().equals("OPTIONS")) {
				return true;
			}
			
			//檢查 userId 是否為空
			if(StringUtils.isEmpty(request.getHeader("userId"))) {
				ActionResult result = new ActionResult(false,ErrorCode.USER_ID_ILLEGAL.getCode() ,ErrorCode.USER_ID_ILLEGAL.getMsg());
				System.out.println("userId 為空");
				setFalseResult(response,result);
				return false;
			}
			
			//從 header 拿取 id 與 token 進行驗證
			Long userId = Long.valueOf(request.getHeader("userId"));
			String token = request.getHeader("userToken");
			Date now = new Date();
			List<UserLoginData> validLoginData = userLoginDataRepository.findByUserIdAndTokenAndValidTimeGreaterThan(userId,token,now);
			
			//token 無效
			if(validLoginData.isEmpty()) {
				ActionResult result = new ActionResult(false,ErrorCode.TOKEN_EXPIRED.getCode() ,ErrorCode.TOKEN_EXPIRED.getMsg());
				setFalseResult(response,result);
				System.out.println("token 過期");
				return false;
			} else {
				return true;
			}
		}catch(Exception ex) {
			ActionResult result = new ActionResult(false,ErrorCode.SYSTEM_ERR.getCode() ,ErrorCode.SYSTEM_ERR.getMsg());
			setFalseResult(response,result);
			System.out.println(ex);
			return false;
		}
		
	}
	
	public void setFalseResult(HttpServletResponse response , ActionResult actionResult) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        response.setHeader("Access-Control-Allow-Headers", "*");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().println(objectMapper.writeValueAsString(actionResult));
    }

}
