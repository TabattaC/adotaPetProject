package com.cupcakeProject.jwt;

import com.cupcakeProject.repository.UserDao;
import com.cupcakeProject.handler.NotFoundException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@Slf4j
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Getter
    private com.cupcakeProject.model.User userDetails;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        userDetails = userDao.findByEmailId(userEmail);
        if (!Objects.isNull(userDetails)) {
            log.info("User found with email: {}", userDetails.getEmail());
            return new User(userDetails.getEmail(), userDetails.getPassword(), new ArrayList<>());
        } else {
            log.warn("User not found with email: {}", userEmail);
            throw new NotFoundException("User Not Found");
        }
    }

}
