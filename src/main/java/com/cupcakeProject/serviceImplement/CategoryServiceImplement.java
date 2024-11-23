package com.cupcakeProject.serviceImplement;

import com.cupcakeProject.jwt.JWTFilter;
import com.cupcakeProject.model.Category;
import com.cupcakeProject.repository.CategoryDao;
import com.cupcakeProject.service.CategoryService;
import com.cupcakeProject.utils.CupcakeProjectUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.cupcakeProject.constants.CupcakeProjectConstants.*;

@Slf4j
@Service
public class CategoryServiceImplement implements CategoryService {
    @Autowired
    CategoryDao categoryDao;

    @Autowired
    JWTFilter jwtFilter;

    @Override

    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCategoryMap(requestMap, false)) {
                    categoryDao.save(getCategoryFromMap(requestMap, false));
                    return CupcakeProjectUtils.getResponseEntity(CATEGORY_ADD, HttpStatus.OK);
                }
            } else {
                return CupcakeProjectUtils.getResponseEntity(UNAUTHORIZED_ACESS, HttpStatus.UNAUTHORIZED);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CupcakeProjectUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try {
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
                log.info("Inside if");
                return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(), HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validateCategoryMap(requestMap, true)) {
                    Optional optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));
                    if (!optional.isEmpty()) {
                        categoryDao.save(getCategoryFromMap(requestMap, true));
                        return CupcakeProjectUtils.getResponseEntity(CATEGORY_UPDATE, HttpStatus.OK);
                    } else {
                        return CupcakeProjectUtils.getResponseEntity(CATEGORY_ID_NOT_EXIST, HttpStatus.OK);
                    }
                }
                return CupcakeProjectUtils.getResponseEntity(INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else {
                return CupcakeProjectUtils.getResponseEntity(UNAUTHORIZED_ACESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CupcakeProjectUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Category getCategoryFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        if (isAdd) {
            category.setId(Integer.parseInt(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }
}
