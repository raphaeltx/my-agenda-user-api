package com.my_agenda_user_api.controller;

import com.my_agenda_user_api.exception.UserNotFoundException;
import com.my_agenda_user_api.model.User;
import com.my_agenda_user_api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> findUser(@PathVariable int id) {
        User user = userService.findUser(id);

        if (user == null) {
            throw new UserNotFoundException("id: " + id);
        }

        EntityModel<User> userModel = EntityModel.of(user);
        WebMvcLinkBuilder selfLink = linkTo(methodOn(this.getClass()).findUser(id));
        userModel.add(selfLink.withRel("all-users"));

        return userModel;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> newUser(@Valid @RequestBody User user) {
        User savedUser = userService.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        System.out.println("Testing");

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteById(@PathVariable int id) {
        userService.deleteById(id);
    }
}
