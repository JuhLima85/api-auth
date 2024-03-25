package com.netdeal.auth.util;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

    public boolean validatePassword(String password) {       
        if (password.length() < 8) {
            return false;
        }
       
        int criteriaMet = 0;
        if (containsUppercase(password)) {
            criteriaMet++;
        }
        if (containsLowercase(password)) {
            criteriaMet++;
        }
        if (containsNumber(password)) {
            criteriaMet++;
        }
        if (containsSymbol(password)) {
            criteriaMet++;
        }
        
        return criteriaMet >= 3;
    }

    private boolean containsUppercase(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsLowercase(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsNumber(String password) {
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsSymbol(String password) {
        String symbols = "!@#$%^&*()-_=+[{]};:'\",<.>/?";
        for (char c : password.toCharArray()) {
            if (symbols.indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }
}


