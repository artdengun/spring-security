package com.deni.springsecurity.entitas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data // untuk generated setter getterr
@NoArgsConstructor // tidak mengenerate argumen consturctor
@AllArgsConstructor // agar generate constuructor
@Entity // untuk membuat table entity
@Table(name = "data_mahasiswa") // membuat tabel data dengan nama data_mahasiwa
public class Mahasiswa {

    @Id // wajib ada mendifinisikan primary key
    @GeneratedValue // identifier menginformasikan bahwa nilai/filed dibuatkan secara otomatis di database
    private Integer id;
    private String nim;
    private String nama;
    private Integer semester;
}
