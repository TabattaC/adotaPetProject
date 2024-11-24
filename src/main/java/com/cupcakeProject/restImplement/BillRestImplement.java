package com.cupcakeProject.restImplement;

import com.cupcakeProject.model.Bill;
import com.cupcakeProject.rest.BillRest;
import com.cupcakeProject.service.BillService;
import com.cupcakeProject.utils.CupcakeProjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.cupcakeProject.constants.CupcakeProjectConstants.SOMETHING_WENT_WRONG;

@RestController
public class BillRestImplement implements BillRest {
    @Autowired
    BillService billService;

    @Override
    public ResponseEntity<String> generatedReport(Map<String, Object> requestBody) {
        try {
            return billService.generatedReport(requestBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CupcakeProjectUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        try {
            return billService.getBills();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>( new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
