package com.netdeal.auth.domain.dtos;

import com.netdeal.auth.domain.enums.UserRole;

public record UserDTO(String login, String password, UserRole role) {
}
