package com.hunseong.basic.auth.oauth.provider;

import lombok.RequiredArgsConstructor;

import java.util.Map;

/**
 * Created by Hunseong on 2022/05/14
 */
@RequiredArgsConstructor
public class NaverOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getUsername() {
        return getProvider() + "_" + getProviderId();
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }
}
