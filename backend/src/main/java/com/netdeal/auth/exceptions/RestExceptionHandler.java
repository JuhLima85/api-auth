package com.netdeal.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFoundException(UserNotFoundException e){
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(UserExistingException.class)
    private ResponseEntity<RestErrorMessage> userExistingException(UserExistingException e){
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(InsufficientLengthException.class)
    private ResponseEntity<RestErrorMessage> insufficientLengthException(InsufficientLengthException e){
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(PasswordCriteriaException.class)
    private ResponseEntity<RestErrorMessage> passwordCriteriaException(PasswordCriteriaException e){
        RestErrorMessage errorMessage = new RestErrorMessage(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorMessage);
    }   

}
