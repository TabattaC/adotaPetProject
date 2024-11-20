package com.adotaPetProject.dao;

import com.adotaPetProject.model.User;
import com.adotaPetProject.wrapper.UserWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmailId(@Param("email") String email);

    List<UserWrapper> getAllUsers();

    @Modifying
    @Transactional
    Integer updateStatus(@Param("status") String status, @Param("id") Integer id);
}
