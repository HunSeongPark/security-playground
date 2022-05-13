package com.hunseong.basic.domain;

import com.hunseong.basic.auth.oauth.provider.OAuth2UserInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Hunseong on 2022/05/13
 */
@Data
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreatedDate
    private LocalDateTime createdDate;

    private String provider;
    private String providerId;

    @Builder
    private User(String username, String password, String email, Role role, String provider, String providerId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    public static User fromOAuth2UserInfo(OAuth2UserInfo userInfo, String password) {
        return User.builder()
                .username(userInfo.getUsername())
                .password(password)
                .email(userInfo.getEmail())
                .role(Role.USER)
                .provider(userInfo.getProvider())
                .providerId(userInfo.getProviderId())
                .build();
    }
}
