package com.netdeal.auth.exceptions;

import com.netdeal.auth.domain.dtos.UserDTO;

public class UserExistingException extends RuntimeException{

    public UserExistingException(UserDTO data) {
        super("O nome de usuário '" + data.login() + "' já está em uso.");
    }
    public UserExistingException(String message) {
        super(message);
    }

}
