package com.cupcakeProject.repository;

import com.cupcakeProject.model.User;
import com.cupcakeProject.wrapper.UserWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<User, Integer> {

    User findByEmailId(@Param("email") String email);

    List<UserWrapper> getAllUsers();

    List<String> getAllAdmin();

    @Modifying
    @Transactional
    Integer updateStatus(@Param("status") String status, @Param("id") Integer id);

    User findByEmail(String email);

}
