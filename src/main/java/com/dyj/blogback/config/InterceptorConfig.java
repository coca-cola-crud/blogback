package com.dyj.blogback.config;

import com.dyj.blogback.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 放行路径
        List<String> patterns = new ArrayList();
        patterns.add("/getTag");
        patterns.add("/getType");
        patterns.add("/blogtest/**");
        patterns.add("/Home");
        patterns.add("/Classify");
        patterns.add("/user/**");
//        patterns.add("/v2/api-docs");
//        patterns.add("/swagger-ui.html");
//        patterns.add("/swagger-resources/**");
        patterns.add("/Login");
        registry.addInterceptor(authInterceptor()).addPathPatterns("/**")
                .excludePathPatterns(patterns);
    }
}
