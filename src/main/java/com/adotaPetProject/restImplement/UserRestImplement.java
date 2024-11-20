package com.adotaPetProject.restImplement;

import com.adotaPetProject.constants.AdotaPetConstants;
import com.adotaPetProject.handler.BusinessException;
import com.adotaPetProject.rest.UserRest;
import com.adotaPetProject.service.UserService;
import com.adotaPetProject.utils.AdotaPetUtils;
import com.adotaPetProject.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.adotaPetProject.constants.AdotaPetConstants.SOMETHING_WENT_WRONG;

@Controller
public class UserRestImplement implements UserRest {
    @Autowired
    UserService userService;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try{
        return userService.signup(requestMap);
        }catch (Exception ex){
            throw new BusinessException(SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return userService.login(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
         return AdotaPetUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try{
            return userService.getAllUsers();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
       try {
            return userService.update(requestMap);
       }catch (Exception e){
           return AdotaPetUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
}
