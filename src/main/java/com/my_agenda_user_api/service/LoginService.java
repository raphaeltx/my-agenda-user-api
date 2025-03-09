package com.my_agenda_user_api.service;

import com.my_agenda_user_api.exception.InvalidCredentialsException;
import com.my_agenda_user_api.exception.UserNotFoundException;
import com.my_agenda_user_api.model.User;
import com.my_agenda_user_api.repository.UserRepository;
import com.my_agenda_user_api.util.JwtUtil;
import org.springframework.stereotype.Component;

@Component
public class LoginService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public LoginService(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public String authenticate(String email, String password) {
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);

        User user = userRepository.findByPersonEmail(email);

        System.out.println("User: " + user);

        if (user == null) {
            throw new UserNotFoundException("Login failed. User not found. Email: " + email);
        }

        if (!user.getPassword().equals(password)) {
            throw new InvalidCredentialsException("Login failed. Invalid password.");
        }

        return jwtUtil.generateToken(email);
    }
}