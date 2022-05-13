package com.hunseong.basic.auth;

import com.hunseong.basic.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Hunseong on 2022/05/14
 * 로그인 완료 시 Security는 Security Session에 Authentication 객체를 저장
 * Authentication 객체는 내부에 UserDetails를 가짐
 * PrincipalDetails - UserDetails를 implements하여 애플리케이션에 맞게 Custom으로 구성
 */
public class PrincipalDetails implements UserDetails {

    private final User user;

    // 일반 로그인
    public PrincipalDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(() -> user.getRole().getValue());
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
