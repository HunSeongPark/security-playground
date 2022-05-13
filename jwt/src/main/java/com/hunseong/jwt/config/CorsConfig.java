package com.hunseong.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Created by Hunseong on 2022/05/14
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); // 서버 응답 시 json을 JS에서 처리 할 수 있도록 설정
        config.addAllowedOrigin("*"); // 모든 ip에 대한 응답 허용
        config.addAllowedHeader("*"); // 모든 header에 대한 응답 허용
        config.addAllowedMethod("*"); // 모든 http method에 대한 응답 허용
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
