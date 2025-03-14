package com.my_agenda_user_api.dto;

import jakarta.validation.constraints.NotNull;

public class LoginRequest {

	@NotNull(message = "Identifier is required.")
	private String identifier;

	@NotNull(message = "Password is required.")
	private String password;

	public LoginRequest() {
	}

	public LoginRequest(String identifier, String password) {
		this.identifier = identifier;
		this.password = password;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginRequest{" + "identifier='" + identifier + '\'' + ", password='" + password + '\'' + '}';
	}

}