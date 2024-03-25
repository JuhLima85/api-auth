package com.netdeal.auth.exceptions;

public class PasswordCriteriaException extends RuntimeException {

    public PasswordCriteriaException() {
        super("A senha não atende aos critérios necessários: Mínimo de 8 caracteres de comprimento, e contém 3/4 dos seguintes itens: letras maiúsculas, letras minúsculas, números e símbolos.");

    }

    public PasswordCriteriaException(String message) {
        super(message);
    }
}

