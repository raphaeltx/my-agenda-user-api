package com.my_agenda_user_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.my_agenda_user_api")
public class MyAgendaUserApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyAgendaUserApiApplication.class, args);
	}

}
