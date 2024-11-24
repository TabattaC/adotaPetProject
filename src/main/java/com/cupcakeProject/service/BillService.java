package com.cupcakeProject.service;

import com.cupcakeProject.model.Bill;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface BillService {
    ResponseEntity<String> generatedReport(Map<String, Object> requestBody);

    ResponseEntity<List<Bill>> getBills();

    ResponseEntity<byte[]> getPdf(Map<String, Object> requestBody);

    ResponseEntity<String>  deleteBill(Integer id);
}
