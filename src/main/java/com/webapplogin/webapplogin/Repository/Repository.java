package com.webapplogin.webapplogin.Repository;

import com.webapplogin.webapplogin.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Repository extends JpaRepository<User, Integer> {
    public User findByUsernameOrEmail(String username, String email);

   // String usernameOrEmail(String username, String email);

   // String email(String email);

    // public User findByEmail(String email);

//Optional<User> è fondamentale per usare il metodo or nel controller quando chiamo questi 2 metodi!!
}