package com.deni.springsecurity.entitas;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "s_Role")
public class Role {

    @Id
    @GeneratedValue
    private Integer id;
    private String nama;
    private String password;
}
