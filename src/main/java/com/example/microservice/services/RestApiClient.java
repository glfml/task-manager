package com.example.microservice.services;

import com.example.microservice.dto.AuthRequest;
import com.example.microservice.dto.AuthResponse;
import com.example.microservice.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class RestApiClient {

    private static String AUTH_URL = "https://gentle-eyrie-95237.herokuapp.com/login";
    private static String FINDALL_URL= "https://gentle-eyrie-95237.herokuapp.com/users";
    private static String GETBYID_URL = "https://gentle-eyrie-95237.herokuapp.com/users/{id}";

    private String username;
    private String password;

    public RestApiClient(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public AuthResponse authenticate()
    {
        RestTemplate restTemplate = getRestTemplate();
        HttpEntity<AuthRequest> request = new HttpEntity<>(new AuthRequest(username, password));

        try {
            AuthResponse response = restTemplate.postForObject(AUTH_URL, request, AuthResponse.class);
            return response;
        } catch (HttpClientErrorException.Forbidden e) {
            return null;
        }
    }

    private RestTemplate getRestTemplate() {

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

        return restTemplate;
    }

    public List<UserResponse> findAllUsers() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<UserResponse>> response = restTemplate.getForObject(FINDALL_URL, null,
                new ParameterizedTypeReference<List<UserResponse>>() {});

        return response.getBody();
    }

    public UserResponse getUserById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Long> request = new HttpEntity<>(id);
        ResponseEntity<UserResponse> response =
                restTemplate.getForEntity(GETBYID_URL.replace("{id}", id.toString()), UserResponse.class);

        return response.getBody();
    }
}
