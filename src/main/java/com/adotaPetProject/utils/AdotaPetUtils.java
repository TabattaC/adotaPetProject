package com.adotaPetProject.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AdotaPetUtils {

    private AdotaPetUtils(){

    }
    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus status){
        return new ResponseEntity<>("{\"message\":\""+ message + "\"}", status);
    }

}
