package com.netdeal.auth;

import static java.util.Optional.empty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.netdeal.auth.domain.dtos.LoginDTO;
import com.netdeal.auth.domain.dtos.UserDTO;
import com.netdeal.auth.domain.enums.UserRole;
import com.netdeal.auth.repositories.UserRepository;
import com.netdeal.auth.services.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.not;
import org.junit.jupiter.api.Order;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorizationService service;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO("User 1", "Password1", UserRole.ADMIN);
    }

    @Test
    @Order(1)
    @WithMockUser
    void register() throws Exception {
        String userJson = objectMapper.writeValueAsString(userDTO);
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].login").value(userDTO.login()))
                .andExpect(jsonPath("$[0].password").isNotEmpty())
                .andExpect(jsonPath("$[0].score").isNumber())
                .andExpect(jsonPath("$[0].role").value(userDTO.role().toString()));
    }

    @Test
    @Order(2)
    @WithMockUser
    void login() throws Exception {
        UserDTO userDTO = new UserDTO("User 2", "Password1", UserRole.ADMIN);
        service.registerUser(userDTO);

        LoginDTO user = new LoginDTO("User 2", "Password1");
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    @Order(2)
    void getAllUsers() throws Exception {
        mockMvc.perform(get("/auth")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").value(not(empty())))
                .andExpect(jsonPath("$[0].login").value(userDTO.login()))
                .andExpect(jsonPath("$[0].role").value(userDTO.role().toString()));
    }

    @Test
    @Order(4)
    @WithMockUser
    void updateUser() throws Exception {
        UserDTO newUserDTO = new UserDTO("NovoLogin", "Password1", UserRole.ADMIN);
        String userJson = objectMapper.writeValueAsString(newUserDTO);
        mockMvc.perform(put("/auth/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value(newUserDTO.login()))
                .andExpect(jsonPath("$.password").isNotEmpty())
                .andExpect(jsonPath("$.role").value(newUserDTO.role().toString()));
    }

    @Test
    @Order(5)
    @WithMockUser
    void deleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/auth/delete/{id}", 2l)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
