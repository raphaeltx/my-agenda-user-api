package com.my_agenda_user_api.repository;

import com.my_agenda_user_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByPersonEmail(String email);

	User findByUserName(String userName);

}
