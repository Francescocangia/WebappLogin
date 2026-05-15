package com.webapplogin.webapplogin.Service;


import com.webapplogin.webapplogin.Repository.Repository;
import com.webapplogin.webapplogin.Entity.User;



import org.aspectj.weaver.ast.Instanceof;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class Service {


    @Autowired
    public Repository repository;


    public List<User> get_all (){


        List<User> lista= new ArrayList<>();
        lista = repository.findAll();
        return lista;
    }



    public User update (Object value, User Updateduser) {



         User user =repository.findByAny((String) value);
         user.setName(Updateduser.getName());
         user.setSurname(Updateduser.getSurname());
         user.setEmail((Updateduser.getName()));
         user.setUsername(Updateduser.getUsername());
         user.setPassword(Updateduser.getPassword());
         user.setRole((Updateduser.getRole()));
         repository.save(user);

        return Updateduser;
    }
}

