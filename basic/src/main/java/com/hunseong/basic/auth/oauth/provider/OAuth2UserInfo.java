package com.hunseong.basic.auth.oauth.provider;

/**
 * Created by Hunseong on 2022/05/14
 */
public interface OAuth2UserInfo {
    String getProvider();
    String getProviderId();
    String getUsername();
    String getEmail();
}
