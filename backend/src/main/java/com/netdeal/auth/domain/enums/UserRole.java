package com.netdeal.auth.domain.enums;

public enum UserRole {
    ADMIN("admin"),
    MODERATOR("moderator"),
    DEPARTMENT_MANAGER("department_manager"),
    USER("user");

    private String role;

    UserRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
