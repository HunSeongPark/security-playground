package com.hunseong.jwt.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by Hunseong on 2022/05/14
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER"),
    MANAGER("ROLE_USER, ROLE_MANAGER"),
    ADMIN("ROLE_USER, ROLE_MANAGER, ROLE_ADMIN");
    private final String value;
}
