package com.adotaPetProject.serviceImplement;

import com.adotaPetProject.constants.AdotaPetConstants;
import com.adotaPetProject.dao.UserDao;
import com.adotaPetProject.handler.BusinessException;
import com.adotaPetProject.model.User;
import com.adotaPetProject.service.UserService;
import com.adotaPetProject.utils.AdotaPetUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

import static com.adotaPetProject.constants.AdotaPetConstants.*;

@Slf4j
@Service
public class UserServiceImplement implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public ResponseEntity<String> signup(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (valideSignUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return AdotaPetUtils.getResponseEntity(SUCCESSFULLY_REGISTERED, HttpStatus.OK);
                } else {
                   return AdotaPetUtils.getResponseEntity(EMAIL_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
                }
            }else{
                return AdotaPetUtils.getResponseEntity(INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new BusinessException(SOMETHING_WENT_WRONG);
        }

    }

    private User getUserFromMap(Map<String,String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

    private boolean valideSignUpMap(Map<String, String> requestMap) {
        return requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email") && requestMap.containsKey("password");
    }
}
