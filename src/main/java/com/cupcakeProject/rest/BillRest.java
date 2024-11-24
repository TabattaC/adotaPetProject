package com.cupcakeProject.rest;

import com.cupcakeProject.model.Bill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/bill")
public interface BillRest {

    @PostMapping(path = "/generatedReport")
    ResponseEntity<String> generatedReport(@RequestBody Map<String, Object> requestBody);

    @GetMapping(path = "/getBills")
    ResponseEntity<List<Bill>>getBills();

}
