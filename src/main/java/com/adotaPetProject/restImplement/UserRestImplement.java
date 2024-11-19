package com.adotaPetProject.restImplement;

import com.adotaPetProject.constants.AdotaPetConstants;
import com.adotaPetProject.handler.BusinessException;
import com.adotaPetProject.rest.UserRest;
import com.adotaPetProject.service.UserService;
import com.adotaPetProject.utils.AdotaPetUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class UserRestImplement implements UserRest {
    @Autowired
    UserService userService;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try{
        return userService.signup(requestMap);
        }catch (Exception ex){
            throw new BusinessException(AdotaPetConstants.SOMETHING_WENT_WRONG);
        }
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return userService.login(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
         return AdotaPetUtils.getResponseEntity(AdotaPetConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
