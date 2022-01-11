package com.mall.client.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.mall.client.repository.UserLoginDataRepository;

@Component
public class AuthInterceptor implements HandlerInterceptor{
	
	@Autowired UserLoginDataRepository userLoginDataRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	throws Exception {
		//從 header 拿取 token 進行驗證
		String token = request.getHeader("userToken");
        System.out.println("Interceptor 拿取 token 認證");
        System.out.println(token);
        return true;
    }
}
