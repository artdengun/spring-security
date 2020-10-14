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
@Table(name = "s_Role")
public class Role {

    @Id
    @GeneratedValue
    private Integer id;
    private String nama;

    @OneToMany(mappedBy = "role")
    private List<UserRoles> list = new ArrayList<>();
}
