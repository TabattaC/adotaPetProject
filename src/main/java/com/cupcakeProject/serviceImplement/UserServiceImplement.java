package com.cupcakeProject.serviceImplement;

import com.cupcakeProject.repository.UserDao;
import com.cupcakeProject.handler.BusinessException;
import com.cupcakeProject.jwt.CustomerUserDetailsService;
import com.cupcakeProject.jwt.JWTFilter;
import com.cupcakeProject.jwt.JwtUtils;
import com.cupcakeProject.model.User;
import com.cupcakeProject.service.UserService;
import com.cupcakeProject.utils.CupcakeProjectUtils;
import com.cupcakeProject.utils.EmailUtils;
import com.cupcakeProject.wrapper.UserWrapper;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.cupcakeProject.constants.AdotaPetConstants.*;

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

    @Autowired
    EmailUtils emailUtils;

    @Override
    public ResponseEntity<String> signup(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
            if (valideSignUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return CupcakeProjectUtils.getResponseEntity(SUCCESSFULLY_REGISTERED, HttpStatus.OK);
                } else {
                    return CupcakeProjectUtils.getResponseEntity(EMAIL_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
                }
            } else {
                return CupcakeProjectUtils.getResponseEntity(INVALID_DATA, HttpStatus.BAD_REQUEST);
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
        try {
            if (jwtFilter.isAdmin()) {
                return new ResponseEntity<>(userDao.getAllUsers(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional<User> optional = userDao.findById(Integer.parseInt(requestMap.get("id")));
                if (!optional.isEmpty()) {
                    userDao.updateStatus(requestMap.get("status"), Integer.parseInt(requestMap.get("id")));
                    sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(), userDao.getAllAdmin());
                    return CupcakeProjectUtils.getResponseEntity(UPDATE_SUCESS, HttpStatus.OK);
                } else {
                    return CupcakeProjectUtils.getResponseEntity(USER_DOESNOT_EXIST, HttpStatus.OK);

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
    public ResponseEntity<String> checkToken() {
        return CupcakeProjectUtils.getResponseEntity("true", HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestmap) {
        try {
            User userObj = userDao.findByEmail(jwtFilter.getCurrentUser());
            if (userObj != null) {
                if (userObj.getPassword().equals(requestmap.get("oldPassword"))) {
                    userObj.setPassword(requestmap.get("newPassword"));
                    userDao.save(userObj);
                    return CupcakeProjectUtils.getResponseEntity(PASSWORD_UPDATED, HttpStatus.OK);
                }
                return CupcakeProjectUtils.getResponseEntity(INCORRECT_OLD_PASSWORD, HttpStatus.BAD_REQUEST);

            }
            return CupcakeProjectUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CupcakeProjectUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestmap) {
        try {
            User user = userDao.findByEmail(requestmap.get("email"));
            if (!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail())) {
                emailUtils.forgotEmail(user.getEmail(), "Credentials by Cupcake Store Managment System", user.getPassword());
            }
            return CupcakeProjectUtils.getResponseEntity(CHECK_EMAIL, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CupcakeProjectUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getCurrentUser());
        if (status != null && status.equalsIgnoreCase("true")) {
            emailUtils.sendSimpleMassage(jwtFilter.getCurrentUser(), "Account Approved", "USER: - " + user + "\n is approved by\nADMIN: - " + jwtFilter.getCurrentUser(), allAdmin);
        } else {
            emailUtils.sendSimpleMassage(jwtFilter.getCurrentUser(), "Account Disabled", "USER: - " + user + "\n is disabled by\nADMIN- " + jwtFilter.getCurrentUser(), allAdmin);

        }
    }

}
