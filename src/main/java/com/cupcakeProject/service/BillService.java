package com.cupcakeProject.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface BillService {
    ResponseEntity<String> generatedReport(Map<String, Object> requestBody);
}
