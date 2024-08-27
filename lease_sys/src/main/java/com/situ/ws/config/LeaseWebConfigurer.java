package com.situ.ws.config;

import com.situ.ws.config.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class LeaseWebConfigurer  implements WebMvcConfigurer {

    public static final String IMG = "img/";
    public static final String PATH = "D:\\WPS Office\\py\\pic\\";
    public static final String FILE = "D:\\WPS Office\\py\\xlsx\\";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("=============静态资源映射================");
        registry.addResourceHandler(IMG + "**").addResourceLocations("file:" + PATH);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        log.info("=============开启拦截器================");

        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/logins/**","/captcha/**","/"+IMG+"**","/loading/**","/users/forgot/**");
    }
}
