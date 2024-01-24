package com.kimdev.kimstagram.Security.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    // 필터가 적용되도록 한다.
    // 대신 이렇게 적용하면 Security Filter Chain보다 늦게 실행됨.
    /*@Bean
    public FilterRegistrationBean<ApplyingFilter> filter1() {
        FilterRegistrationBean<ApplyingFilter> bean = new FilterRegistrationBean<>(new ApplyingFilter());
        bean.addUrlPatterns("/*"); // 모든 요청에서 다 필터
        bean.setOrder(0); // 이 필터의 우선순위 설정 -> 낮을수록 가장 먼저 실행돼서 이 필터가 가장 먼저 실행된다.
        return bean;
    }*/
}
