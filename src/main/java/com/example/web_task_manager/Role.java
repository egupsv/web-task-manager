package com.example.web_task_manager;

public enum Role {
    ADMIN(0),
    USER(1000);

    public final static Role DEFAULT_ROLE = USER;

    private final int roleLevel;

    Role(int roleLevel) {
        this.roleLevel = roleLevel;
    }

    public int getRoleLevel() {
        return roleLevel;
    }

}
