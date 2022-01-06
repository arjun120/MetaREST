package com.arjun.metarest.domain;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDetails {
    @NotNull
    @JsonProperty
    private String username;

    @NotNull
    @JsonProperty
    private String password;

    public UserDetails() {
        //Jackson deserialization
    }

    public UserDetails(String username, String password) {
        this.username=username;
        this.password=password;
    }


    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }
}
