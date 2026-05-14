package com.webapplogin.webapplogin.Controller;


import ch.qos.logback.core.net.SyslogOutputStream;
import com.webapplogin.webapplogin.DTO.UserDto;
import com.webapplogin.webapplogin.Repository.Repository;
import com.webapplogin.webapplogin.Service.Service;
import com.webapplogin.webapplogin.Entity.User;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class Controller {

    @Autowired
    public Service service;
    @Autowired
    private Repository repository;

    @GetMapping("/getall")
    public ResponseEntity<List<User>> getall() {
        List<User> lista = service.get_all();

        return ResponseEntity.ok(lista);
    }
}

