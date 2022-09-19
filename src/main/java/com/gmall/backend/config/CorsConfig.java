package com.gmall.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CorsConfig {

    private static final long MAX_AGE = 24 * 60 * 60;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        List<String> allowedOriginPatterns = new ArrayList<>();
        allowedOriginPatterns.add("*");
        CorsConfiguration corsConfiguration = new CorsConfiguration();// 1 设置访问源地址
        corsConfiguration.addAllowedHeader("*");//哪些头
        corsConfiguration.addAllowedMethod("*");//哪些方式 get post 。。。
        corsConfiguration.setAllowedOriginPatterns(allowedOriginPatterns);//从哪个域名来的请求 不要写 * 会报错
        corsConfiguration.setAllowCredentials(true);//允许cookie
        corsConfiguration.setMaxAge(MAX_AGE);
        source.registerCorsConfiguration("/**", corsConfiguration); // 4 对接口配置跨域设置
        return new CorsFilter(source);
    }
}
