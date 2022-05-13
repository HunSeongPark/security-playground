package com.hunseong.basic.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by Hunseong on 2022/05/13
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER"), MANAGER("ROLE_MANAGER"), ADMIN("ROLE_ADMIN");

    private final String value;
}
