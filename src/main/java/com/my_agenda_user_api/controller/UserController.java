package com.my_agenda_user_api.controller;

import com.my_agenda_user_api.model.ApiResponse;
import com.my_agenda_user_api.model.User;
import com.my_agenda_user_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<EntityModel<User>>> findUser(@PathVariable int id) {
		User user = userService.findById(id);
		EntityModel<User> userModel = EntityModel.of(user);
		ApiResponse<EntityModel<User>> response = new ApiResponse<>(true, "User found", userModel);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Void>> createUser(@Valid @RequestBody User user) {
		User savedUser = userService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		ApiResponse<Void> response = new ApiResponse<>(true, "User created", null);
		return ResponseEntity.created(location).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteUserById(@PathVariable int id) {
		userService.deleteById(id);
		ApiResponse<Void> response = new ApiResponse<>(true, "User deleted", null);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
		List<User> users = userService.findAll();
		ApiResponse<List<User>> response = new ApiResponse<>(true, "Users retrieved", users);
		return ResponseEntity.ok(response);
	}

}