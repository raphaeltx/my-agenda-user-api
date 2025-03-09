package com.my_agenda_user_api.service;

import com.my_agenda_user_api.exception.PersonNotFoundException;
import com.my_agenda_user_api.exception.UserNotFoundException;
import com.my_agenda_user_api.model.User;
import com.my_agenda_user_api.repository.PersonRepository;
import com.my_agenda_user_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    private final UserRepository userRepository;
    private final PersonRepository personRepository;

    public UserService(UserRepository userRepository, PersonRepository personRepository) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
    }

    public User findById(int id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException("User not found. Id: " + id);
        }

        if (user.getPerson() == null) {
            throw new PersonNotFoundException("Person not found. Id: " + id);
        }

        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public User save(User user) {
        if (user.getPerson() == null) {
            throw new PersonNotFoundException("Person information is required.");
        }

        personRepository.save(user.getPerson());
        return userRepository.save(user);
    }

    @Transactional
    public void deleteById(int id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new UserNotFoundException("User not found. Id: " + id);
        }

        if (user.getPerson() == null) {
            throw new PersonNotFoundException("Person not found. Id: " + id);
        }

        personRepository.deleteById(user.getPerson().getId());
        userRepository.deleteById(id);
    }

    public List<User> findAll() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new UserNotFoundException("No users found. ");
        }

        return userRepository.findAll();
    }
}
