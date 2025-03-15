package com.my_agenda_user_api.controller;

import com.my_agenda_user_api.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping("/health")
	public ResponseEntity<ApiResponse<String>> healthCheck() {
		ApiResponse<String> response = new ApiResponse<>(true, "Application is running", "OK");
		return ResponseEntity.ok(response);
	}

}