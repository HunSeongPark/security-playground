package com.hunseong.basic.config;

import com.hunseong.basic.domain.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by Hunseong on 2022/05/13
 */
@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터 체인에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**")
                .authenticated() // 인증 완료 시 접근 가능
                .antMatchers("/manager/**")
                .hasAnyRole(Role.MANAGER.name(), Role.ADMIN.name()) // 해당하는 Role만 접근 가능
                .antMatchers("/admin/**")
                .hasRole(Role.ADMIN.name())
                .anyRequest()
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm") // 로그인 페이지 url
                .loginProcessingUrl("/login") // Security가 login process를 실행할 url
                .defaultSuccessUrl("/") // 로그인 성공 시 redirect 될 기본 url
                .and()
                .oauth2Login()
                .loginPage("/loginForm")
                .userInfoEndpoint()
                .userService(principalOAuth2UserService); // 로그인 완료 후 Token + 사용자 프로필 받아옴
    }
}
