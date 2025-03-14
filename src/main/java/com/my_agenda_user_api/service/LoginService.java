package com.my_agenda_user_api.service;

import com.my_agenda_user_api.exception.InvalidCredentialsException;
import com.my_agenda_user_api.exception.UserNotFoundException;
import com.my_agenda_user_api.model.User;
import com.my_agenda_user_api.repository.UserRepository;
import com.my_agenda_user_api.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoginService {

	private final JwtUtil jwtUtil;

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder passwordEncoder;

	public LoginService(JwtUtil jwtUtil, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		this.jwtUtil = jwtUtil;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public String authenticate(String identifier, String password) {
		User user = findUserByIdentifier(identifier);
		validatePassword(password, user.getPassword());
		return jwtUtil.generateToken(identifier);
	}

	private User findUserByIdentifier(String identifier) {
		User user = userRepository.findByPersonEmail(identifier);
		if (user == null) {
			user = userRepository.findByUserName(identifier);
		}
		if (user == null) {
			throw new UserNotFoundException("Login failed. User not found. Identifier: " + identifier);
		}
		return user;
	}

	private void validatePassword(String rawPassword, String encodedPassword) {
		if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
			throw new InvalidCredentialsException("Login failed. Invalid password.");
		}
	}

}