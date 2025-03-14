package com.my_agenda_user_api.service;

import com.my_agenda_user_api.exception.PersonNotFoundException;
import com.my_agenda_user_api.exception.UserNotFoundException;
import com.my_agenda_user_api.model.User;
import com.my_agenda_user_api.repository.PersonRepository;
import com.my_agenda_user_api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;

	private final PersonRepository personRepository;

	private final BCryptPasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PersonRepository personRepository,
			BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.personRepository = personRepository;
		this.passwordEncoder = passwordEncoder;
		logger.info("UserService instantiated");
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

		user.setPassword(passwordEncoder.encode(user.getPassword()));
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
			throw new UserNotFoundException("No users found.");
		}

		return userRepository.findAll();
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with userName: " + userName);
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				new ArrayList<>());
	}

}