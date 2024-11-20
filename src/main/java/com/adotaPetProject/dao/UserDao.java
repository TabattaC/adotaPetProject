package com.adotaPetProject.dao;

import com.adotaPetProject.model.User;
import com.adotaPetProject.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<User,Integer> {

    User findByEmailId(@Param("email")String email);
    List<UserWrapper> getAllUsers();
}
