package com.my_agenda_user_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class  User {

    private Integer id;

    @Email(message = "Invalid Email.")
    @Size(min = 2, message = "Min 2.")
    @JsonProperty("email")
    private String email;

    @Size(min = 2)
    @JsonProperty("user_name")
    private String userName;

    public User() {
    }

    public User(Integer id, String email, String userName) {
        this.id = id;
        this.email = email;
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
