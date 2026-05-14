package com.webapplogin.webapplogin.Service;

import com.webapplogin.webapplogin.Entity.User;
import com.webapplogin.webapplogin.Repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {


   @Autowired
   public Repository repository;

    @Override
    public UserDetails loadUserByUsername( String login ) throws UsernameNotFoundException {
        User user = repository.findByUsernameOrEmail(login, login );



        return org.springframework.security.core.userdetails.User

                .withUsername(user.getUsername())
                . password(user.getPassword()).
        authorities ("ROLE_" + user.getRole())
                .build();

    };
    }

