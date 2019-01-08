package com.example.microservice.dto;

import java.util.Date;

public class UserResponse {

    public Date birthDate;
    public City city;
    public String firstName;
    public Long id;
    public String lastName;

    public class City {
        public Long id;
        public String name;
    }

}
