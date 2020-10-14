package com.deni.springsecurity.entitas;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "s_user")
public class User {

    private Integer id;
    private String name;
    private String password;
    private boolean active;
}
