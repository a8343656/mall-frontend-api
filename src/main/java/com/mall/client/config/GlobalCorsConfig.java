package com.mall.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class GlobalCorsConfig implements WebMvcConfigurer {
 
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")		//允許跨域的路徑
                .allowedHeaders("*")	//允許請求的 header
                .allowedMethods("*")	//允許的http方法
                .allowCredentials(true)
                .maxAge(1800)			//preflight request 過期時間
                .allowedOrigins("http://localhost:8080" 
                				,"http://192.168.50.93:81"
                				,"http://ulandg254-1.tc.kingnet.net.tw:8082"); //允許跨域的網域
    }
}
