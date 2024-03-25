package com.netdeal.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.netdeal.auth.domain.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login);

    Optional<UserDetails> findById(long id);
}
