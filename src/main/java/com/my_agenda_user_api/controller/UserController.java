package com.my_agenda_user_api.controller;

import com.my_agenda_user_api.model.User;
import com.my_agenda_user_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

	@GetMapping("/users/{id}")
	public EntityModel<User> findUser(@PathVariable int id) {
		User user = userService.findById(id);
		EntityModel<User> userModel = EntityModel.of(user);
		WebMvcLinkBuilder selfLink = linkTo(methodOn(this.getClass()).findUser(id));
		userModel.add(selfLink.withRel("self"));
		return userModel;
	}

	@PostMapping
	public ResponseEntity<Void> createUser(@Valid @RequestBody User user) {
		User savedUser = userService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable int id) {
		userService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.findAll();
		return ResponseEntity.ok(users);
	}

}