package com.hunseong.jwt.config;

import com.hunseong.jwt.config.jwt.JwtAuthenticationFilter;
import com.hunseong.jwt.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

import static com.hunseong.jwt.domain.Role.*;

/**
 * Created by Hunseong on 2022/05/14
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 X
                .and()
                .addFilter(corsFilter)
                .formLogin().disable()
                .httpBasic().disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")
                .authenticated()
                .antMatchers("/api/v1/manager/**")
                .hasAnyRole(MANAGER.name(), ADMIN.name())
                .antMatchers("/api/v1/admin/**")
                .hasAnyRole(ADMIN.name())
                .anyRequest()
                .permitAll();
    }
}
