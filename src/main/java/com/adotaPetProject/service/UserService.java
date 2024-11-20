package com.adotaPetProject.service;

import com.adotaPetProject.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    ResponseEntity<String> signup(Map<String,String> requestMap);

    ResponseEntity<String> login(Map<String,String> requestMap);

    ResponseEntity<List<UserWrapper>> getAllUsers();
}
