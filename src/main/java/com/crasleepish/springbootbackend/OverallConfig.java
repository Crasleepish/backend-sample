package com.crasleepish.springbootbackend;

import com.crasleepish.springbootbackend.util.interceptor.ExampleInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OverallConfig implements WebMvcConfigurer {

    //所有自定义拦截器在这里注册
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册一个ExampleInterceptor，只对以下地址模式生效
        registry.addInterceptor(new ExampleInterceptor())
                .addPathPatterns("/classinfo/**", "/user/**", "/articles/**");
    }

    //添加静态资源映射后，才能正常访问静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
