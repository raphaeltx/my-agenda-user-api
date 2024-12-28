package com.my_agenda_user_api.service;

import com.my_agenda_user_api.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    public User findUser(int id) {
        return new User(1, "test@test.com", "test");
    }

    public User save(User user) {
        System.out.println("Saving user: " + user);
        return user;
    }

    public void deleteById(int id) {
        System.out.println("Deleting user: id = " + id);
    }


}
