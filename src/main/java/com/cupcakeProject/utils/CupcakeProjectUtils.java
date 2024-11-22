package com.cupcakeProject.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CupcakeProjectUtils {

    private CupcakeProjectUtils(){

    }
    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus status){
        return new ResponseEntity<>("{\"message\":\""+ message + "\"}", status);
    }

}
