package com.hot.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hot.common.interceptor.MyInterceptor;
import com.hot.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor(sysUserService, objectMapper))
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/fs/init", "/fs/user");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                // Content-Disposition：导出文件下载时，前端跨域需读取此头获取文件名，否则浏览器默认不暴露
                .exposedHeaders("Authorization", "Content-Type", "Content-Disposition")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
