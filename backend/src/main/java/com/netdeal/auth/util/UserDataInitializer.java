package com.netdeal.auth.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.netdeal.auth.domain.dtos.UserDTO;
import com.netdeal.auth.domain.enums.UserRole;
import com.netdeal.auth.exceptions.PasswordCriteriaException;
import com.netdeal.auth.exceptions.UserExistingException;
import com.netdeal.auth.exceptions.UserNotFoundException;
import com.netdeal.auth.services.AuthorizationService;

@Component
@Profile("!test")
public class UserDataInitializer implements CommandLineRunner {

    private final AuthorizationService authorizationService;    

    public UserDataInitializer(AuthorizationService authorizationService, PasswordValidator passwordValidator) {
        this.authorizationService = authorizationService;        
    }

    @Override
    public void run(String... args) {      
        UserDTO[] initialUsers = {
                new UserDTO("User 1", "Password1!", UserRole.USER),
                new UserDTO("User 2", "StrongP@ssword123", UserRole.ADMIN),
                new UserDTO("User 3", "SecurePassword!", UserRole.MODERATOR),               
        };

        for (UserDTO userData : initialUsers) {
            try {
                authorizationService.registerUser(userData);
            } catch (UserExistingException | PasswordCriteriaException | UserNotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
