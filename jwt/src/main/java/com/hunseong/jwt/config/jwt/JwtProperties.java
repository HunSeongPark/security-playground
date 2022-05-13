package com.hunseong.jwt.config.jwt;

/**
 * Created by Hunseong on 2022/05/14
 */
public abstract class JwtProperties {

    public static final String SUBJECT = "Subject";
    public static final String SECRET = "Secret";
    public static final int EXPIRATION_TIME = 1000 * 3600 * 24 * 10; // 10 day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
