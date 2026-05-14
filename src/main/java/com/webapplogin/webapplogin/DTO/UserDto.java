package com.webapplogin.webapplogin.DTO;


import com.webapplogin.webapplogin.Entity.User;
import lombok.Data;

@Data
public class UserDto {


    public Integer id;
    public String username;
    public String password;
    public String email;
    public String role;


}