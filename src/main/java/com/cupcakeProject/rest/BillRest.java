package com.cupcakeProject.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Objects;

@RequestMapping(path = "/bill")
public interface BillRest {

    @PostMapping(path = "/generatedReport")
    ResponseEntity<String> generatedReport(@RequestBody Map<String, Object> requestBody);
}
