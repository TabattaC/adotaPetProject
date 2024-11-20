package com.adotaPetProject.serviceImplement;

import com.adotaPetProject.constants.AdotaPetConstants;
import com.adotaPetProject.dao.UserDao;
import com.adotaPetProject.handler.BusinessException;
import com.adotaPetProject.jwt.CustomerUserDetailsService;
import com.adotaPetProject.jwt.JWTFilter;
import com.adotaPetProject.jwt.JwtUtils;
import com.adotaPetProject.model.User;
import com.adotaPetProject.service.UserService;
import com.adotaPetProject.utils.AdotaPetUtils;
import com.adotaPetProject.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.adotaPetProject.constants.AdotaPetConstants.*;

@Slf4j
@Service
public class UserServiceImplement implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CustomerUserDetailsService customerUserDetailsService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    JWTFilter jwtFilter;

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
            } else {
                return AdotaPetUtils.getResponseEntity(INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            throw new BusinessException(SOMETHING_WENT_WRONG);
        }

    }


    private User getUserFromMap(Map<String, String> requestMap) {
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

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Login.");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );
            if (authentication.isAuthenticated()) {
                if (customerUserDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\":\"" + jwtUtils.generateToken(customerUserDetailsService.getUserDetails().getEmail(),
                            customerUserDetailsService.getUserDetails().getRole()) + "\"}", HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("{\"message\":\"" + " Wait for admin approval." + "\"}", HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            log.error("{}", e);
        }
        return new ResponseEntity<String>("{\"message\":\"" + " Bad Credentials." + "\"}", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try{
            if(jwtFilter.isAdmin()){
                return new ResponseEntity<>(userDao.getAllUsers(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
