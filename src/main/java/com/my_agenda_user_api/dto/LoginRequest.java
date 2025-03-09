package com.my_agenda_user_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class LoginRequest {
    @NotNull(message = "Email is required.")
    @Email(message = "Email should be valid.")
    private String email;

    @NotNull(message = "Password is required.")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}