package com.example.microservice.dto;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Calendar;
import java.util.Date;

public class AuthResponse {

    private String token;

    @JsonProperty("expires_in")
    private Long expiresIn;

    private Date createdAt;

    public AuthResponse() {
        this.createdAt = new Date();
    }

    public AuthResponse(String token, Long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.createdAt = new Date();
    }

    public String getToken() {
        return token;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public boolean isExpired()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, expiresIn.intValue());

        return createdAt.after(calendar.getTime());
    }
}
