package com.linseven.userservice.config;

import com.linseven.userservice.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/10 15:24
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    /**
     * 注册拦截器
     * @return
     */
    @Bean
    public JwtInterceptor getJwtInterceptor(){
        return new JwtInterceptor();
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(getJwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/signUp")
                .excludePathPatterns("/favicon.ico");
    }

    /**
     * 解决 No mapping for GET /favicon.ico 访问静态资源图标
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //addResourceHandler("/qy/**") 所有/qy/开头的请求 都会去后面配置的路径下查找资源
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/");
        //图片真实存放的路径
        //registry.addResourceHandler("/upload/avatar/**").addResourceLocations("file:"+System.getProperty("user.dir")+"/upload/avatar/");
        super.addResourceHandlers(registry);
    }
}

