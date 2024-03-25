package com.netdeal.auth.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.netdeal.auth.domain.dtos.LoginDTO;
import com.netdeal.auth.domain.dtos.UserDTO;
import com.netdeal.auth.domain.user.User;
import com.netdeal.auth.services.AuthorizationService;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthorizationService service;

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        List<User> users = this.service.getAll();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<List<User>> register(@RequestBody @Valid UserDTO data){
        List<User> orderedUsers = this.service.registerUser(data);
        return ResponseEntity.ok(orderedUsers);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable("id") long id, @RequestBody @Valid UserDTO data ) {
        System.out.println("Data received from view: " + data);
        User userUpdate = service.updateUser(id, data);
        return ResponseEntity.ok().body(userUpdate);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<User> delete(@PathVariable("id") long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    }
