package com.webapplogin.webapplogin.Repository;

import com.webapplogin.webapplogin.Entity.User;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface Repository extends JpaRepository<User, Integer> {
    public User findByUsernameOrEmail(String username, String email);
    @Query("""
        SELECT u
        FROM User u
        WHERE cast (u.id AS STring)= :value
           OR u.email = :value
           OR u.username = :value
    """)
    User findByAny(@Param("value") String value);
//Optional<User> è fondamentale per usare il metodo or nel controller quando chiamo questi 2 metodi!!
}