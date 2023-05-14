package com.linseven.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/13 10:42
 */
@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Bean
    public  ThreadLocal threadLocal(){
       return new ThreadLocal<>();
    }
}
