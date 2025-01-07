package com.interprep.quiz_service.service;

import com.interprep.quiz_service.dao.UserRepo;
import com.interprep.quiz_service.model.User;
import com.interprep.quiz_service.model.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user =repo.findByUsername(username);

        if(user==null)
        {
            throw new UsernameNotFoundException(("User 404"));
        }

        return new UserPrincipal(user);
    }
}