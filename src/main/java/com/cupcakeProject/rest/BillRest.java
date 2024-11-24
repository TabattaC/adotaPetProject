package com.cupcakeProject.rest;

import com.cupcakeProject.model.Bill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/bill")
public interface BillRest {

    @PostMapping(path = "/generatedReport")
    ResponseEntity<String> generatedReport(@RequestBody Map<String, Object> requestBody);

    @GetMapping(path = "/getBills")
    ResponseEntity<List<Bill>>getBills();
    @PostMapping(path = "/getPdf")
    ResponseEntity<byte[]> getPdf(@RequestBody Map<String, Object> requestBody);

    @PostMapping(path = "/delete/{id}")
    ResponseEntity<String> deleteBill(@PathVariable Integer id);

}
