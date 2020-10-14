package com.deni.springsecurity.entitas;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "s_user")
public class User {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String password;
    private boolean active;

    @OneToMany(mappedBy = "user")
    private List<UserRoles> roles = new ArrayList<>();
 }
