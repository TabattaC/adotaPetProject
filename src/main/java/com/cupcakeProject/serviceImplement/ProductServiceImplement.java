package com.cupcakeProject.serviceImplement;

import com.cupcakeProject.jwt.JWTFilter;
import com.cupcakeProject.model.Category;
import com.cupcakeProject.model.Product;
import com.cupcakeProject.repository.ProductDao;
import com.cupcakeProject.service.ProductService;
import com.cupcakeProject.utils.CupcakeProjectUtils;
import com.cupcakeProject.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.cupcakeProject.constants.CupcakeProjectConstants.*;

@Service
public class ProductServiceImplement implements ProductService {
    @Autowired
    ProductDao productDao;
    @Autowired
    JWTFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateProductMap(requestMap, false)) {
                    productDao.save(getProductFromMap(requestMap, false));
                    return CupcakeProjectUtils.getResponseEntity(PRODUCT_ADD, HttpStatus.OK);
                }
                return CupcakeProjectUtils.getResponseEntity(INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else {
                return CupcakeProjectUtils.getResponseEntity(UNAUTHORIZED_ACESS, HttpStatus.UNAUTHORIZED);

            }
        } catch (Exception e) {

        }
        return CupcakeProjectUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setId(Integer.parseInt(requestMap.get("categoryId")));

        Product product = new Product();
        if (isAdd) {
            product.setId(Integer.parseInt(requestMap.get("id")));
        } else {
            product.setStatus("true");
        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));
        return product;
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {
        try {
           return new ResponseEntity<>(productDao.getAllProduct(),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
