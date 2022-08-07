package com.levelup.core.domain.role;

import lombok.Getter;

@Getter
public enum RoleName {
    ADMIN("ROLE_ADMIN"), CHANNEL_MANAGER("ROLE_CHANNEL_MANAGER"), MEMBER("ROLE_MEMBER"), ANONYMOUS("ROLE_ANONYMOUS");

    private String name;

    RoleName(String roleName) {
        this.name = roleName;
    }
}
