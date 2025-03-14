package com.my_agenda_user_api.controller;

import com.my_agenda_user_api.dto.LoginRequest;
import com.my_agenda_user_api.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

	private final LoginService loginService;

	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}

	@PostMapping
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
		try {
			String token = loginService.authenticate(loginRequest.getIdentifier(), loginRequest.getPassword());
			return ResponseEntity.ok(token);
		}
		catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}

}