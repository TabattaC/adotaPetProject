package com.cupcakeProject.restImplement;

import com.cupcakeProject.rest.ProductRest;
import com.cupcakeProject.service.ProductService;
import com.cupcakeProject.utils.CupcakeProjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.cupcakeProject.constants.CupcakeProjectConstants.SOMETHING_WENT_WRONG;

@RestController
public class ProductRestImplement implements ProductRest {
    @Autowired
    ProductService productService;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            return productService.addNewProduct(requestMap);
        }catch (Exception e){

        }
        return CupcakeProjectUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
