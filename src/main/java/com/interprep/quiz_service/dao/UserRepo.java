package com.interprep.quiz_service.dao;

import com.interprep.quiz_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {


    public User findByUsername(String username);
}
