package com.mall.client.interceptor;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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
			
			//檢查 userId 是否為空
			if(request.getHeader("userId").isEmpty()) {
				response.getWriter().write("userId is null");
				response.setStatus(401);
				return false;
			}
			
			//從 header 拿取 id 與 token 進行驗證
			Long userId = Long.valueOf(request.getHeader("userId"));
			String token = request.getHeader("userToken");
			Date now = new Date();
			List<UserLoginData> validLoginData = userLoginDataRepository.findByUserIdAndTokenAndValidTimeGreaterThan(userId,token,now);
			
			//token 無效
			if(validLoginData.isEmpty()) {
				response.getWriter().write("token expired");
		        response.setStatus(401);
				return false;
			} else {
				return true;
			}
		}catch(Exception ex) {
			response.getWriter().write("server error plz check log");
			response.setStatus(500);
			return false;
		}
		
	}

}
