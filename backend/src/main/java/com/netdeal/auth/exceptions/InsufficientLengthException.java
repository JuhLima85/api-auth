package com.netdeal.auth.exceptions;

public class InsufficientLengthException extends RuntimeException {
    public InsufficientLengthException() {
        super("A senha não atende ao comprimento mínimo");
    }
}
