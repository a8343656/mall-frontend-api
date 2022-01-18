package com.mall.client.interceptor;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.mall.client.entity.UserLoginData;
import com.mall.client.repository.UserLoginDataRepository;

@Component
public class AuthInterceptor implements HandlerInterceptor{
	
	@Autowired UserLoginDataRepository userLoginDataRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	throws Exception {
		try {
			//從 header 拿取 token 進行驗證
			Long userId = Long.valueOf(request.getHeader("userId"));
			String token = request.getHeader("userToken");
			Date now = new Date();
			List<UserLoginData> validLoginData = userLoginDataRepository.findByUserIdAndTokenAndValidTimeGreaterThan(userId,token,now);
			
			if(validLoginData.isEmpty()) {
				response.getWriter().write("token expired");
		        response.setStatus(401);
				return false;
			} else {
				return true;
			}
		}catch(NumberFormatException nm) {
			response.getWriter().write("userId formate not correct");
	        response.setStatus(401);
			return false;
		}

    }
}
