package com.epra.wiki.config;

import com.epra.wiki.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @Author: Guotao Li
 * @DateTime: 2022/8/20 10:30 上午
 * @Description: TODO
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Resource
    LogInterceptor logInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor).addPathPatterns("/**").excludePathPatterns("/login");
    }
}
