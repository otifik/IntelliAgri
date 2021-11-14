package com.jit.authority.config;

import com.jit.authority.jwtsecurity.bean.JwtProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Bean
    @ConfigurationProperties(prefix = "jwt.config")
    public JwtProperty jwt() {
        return new JwtProperty();
    }
}
