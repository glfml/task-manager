package com.example.microservice.services;

import com.example.microservice.entity.User;
import com.example.microservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManager {

    @Autowired
    private UserRepository userRepository;

    public User find(Long id)
    {
        return userRepository.getOne(id);
    }
}
