package com.ssafy.bjbj.api.member.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    MEMBER(ROLES.MEMBER, "멤버권한"),
    ADMIN(ROLES.ADMIN, "관리자권한");

    public static class ROLES {
        public static final String MEMBER = "ROLE_MEMBER";
        public static final String ADMIN = "ROLE_ADMIN";
    }

    private final String authority;
    private final String description;

    Role(String authority, String description) {
        this.authority = authority;
        this.description = description;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public String getDescription() {
        return description;
    }

}
