package com.deni.springsecurity.entitas;


import javax.persistence.*;

@Entity
@Table(name = "s_user_roles")
public class UserRoles {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    private Role role;
}
