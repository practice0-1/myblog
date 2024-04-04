package com.blog5.payload;

import lombok.Data;

import javax.persistence.Column;

@Data
public class SignUpDto {
    private Long id;


    private String email;


    private String name;


    private String username;


    private String password;
}
