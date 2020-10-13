package com.deni.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // mendefinisikan bahwa ini adalah class controller
@RequestMapping("/mahasiswa")
public class MahasiswaController {

        @GetMapping("/new")
        public String newMahasiswa(){
            return "/mahasiswa/TambahMahasiswa";
        }
}
