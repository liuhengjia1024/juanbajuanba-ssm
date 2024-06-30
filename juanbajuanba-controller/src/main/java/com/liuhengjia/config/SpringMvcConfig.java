package com.liuhengjia.config;

import com.liuhengjia.interceptor.AdministratorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.liuhengjia.controller")
@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringMvcConfig implements WebMvcConfigurer {
    @Bean("multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(104857600);
        resolver.setMaxUploadSizePerFile(104857600);
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdministratorInterceptor())
                .addPathPatterns("/pages/admin-**")
                .excludePathPatterns("/pages/admin-sign-**");
    }
}
