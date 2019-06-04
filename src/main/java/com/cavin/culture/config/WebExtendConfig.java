package com.cavin.culture.config;

import com.cavin.culture.controller.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebExtendConfig {

    @Bean
    public WebMvcConfigurer corConfigurer() {
        return new WebMvcConfigurer() {
            // 配置跨域
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization", "x-auth-token")
                        .exposedHeaders("x-auth-token")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public WebMvcConfigurer interceptorConfigurer() {
        return new WebMvcConfigurer() {
            // 配置拦截器
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/admin/isLogin", "/admin/register", "/admin/login");
            }
        };
    }

}
