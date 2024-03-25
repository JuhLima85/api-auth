package com.netdeal.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.netdeal.auth.domain.dtos.UserDTO;
import com.netdeal.auth.domain.user.User;
import com.netdeal.auth.exceptions.PasswordCriteriaException;
import com.netdeal.auth.exceptions.UserExistingException;
import com.netdeal.auth.exceptions.UserNotFoundException;
import com.netdeal.auth.repositories.UserRepository;
import com.netdeal.auth.util.PasswordStrengthCalculator;
import com.netdeal.auth.util.PasswordValidator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Autowired
    private PasswordValidator passwordValidator;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }

    public List<User> registerUser(UserDTO data) {
        if (data.login() == null || data.login().isEmpty()) {
            throw new UserNotFoundException("O campo 'Login' é obrigatório.");
        } else if (data.password() == null || data.password().isEmpty()) {
            throw new UserNotFoundException("O campo 'Password' é obrigatório.");
        } else if (data.role() == null) {
            throw new UserNotFoundException("O campo 'Role' é obrigatório.");
        }
        if (repository.findByLogin(data.login()) != null) {
            throw new UserExistingException(data);
        }
        
        if (!passwordValidator.validatePassword(data.password())) {
            throw new PasswordCriteriaException();
        }
       
        int score = PasswordStrengthCalculator.calculatePasswordStrength(data.password());

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUer = new User(data.login(), encryptedPassword, score, data.role());
        repository.save(newUer);

        List<User> allUsers = repository.findAll();

        return orderedByRole(allUsers);
    }

    public User updateUser(Long id, UserDTO data) {
        if (data.login() == null || data.login().isEmpty()) {
            throw new UserNotFoundException("O campo 'Login' é obrigatório.");
        } else if (data.password() == null || data.password().isEmpty()) {
            throw new UserNotFoundException("O campo 'Password' é obrigatório.");
        } else if (data.role() == null) {
            throw new UserNotFoundException("O campo 'Role' é obrigatório.");
        }

        User user = repository.findById(id)
                .map(User::new)
                .orElseThrow(UserNotFoundException::new);

        user.setLogin(data.login());
        user.setPassword(new BCryptPasswordEncoder().encode(data.password()));
        user.setRole(data.role());
        user.setId(id);

        return repository.save(user);
    }

    public void deleteUser(long id) {
        User user = (User) repository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        repository.delete(user);
    }

    public List<User> getAll(){
        return this.repository.findAll();
    }
    
    public List<User> orderedByRole(List<User> users) {
        return users.stream()
                .sorted(Comparator.comparing(user -> user.getRole().ordinal()))
                .collect(Collectors.toList());
    }
}
