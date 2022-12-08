package com.levelup.member.domain.constant;

import lombok.Getter;

@Getter
public enum RoleName {
    ADMIN("ROLE_ADMIN", 1),
    CHANNEL_MANAGER("ROLE_CHANNEL_MANAGER", 2),
    MEMBER("ROLE_MEMBER", 3),
    ANONYMOUS("ROLE_ANONYMOUS", 4);

    private String name;
    private int priority;

    RoleName(String roleName, int priority) {
        this.name = roleName;
        this.priority = priority;
    }
}
