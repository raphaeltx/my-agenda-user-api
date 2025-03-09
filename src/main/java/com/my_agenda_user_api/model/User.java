package com.my_agenda_user_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity(name = "user_info")
public class  User {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 2)
    @NotNull(message = "User name is required.")
    @JsonProperty("user_name")
    private String userName;

    @Size(min = 2)
    @NotNull(message = "Password is required.")
    @JsonProperty("password")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull(message = "Person information is required.")
    private Person person;

    public User() {
    }

    public User(Long id, String email, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", person=" + person +
                '}';
    }
}
