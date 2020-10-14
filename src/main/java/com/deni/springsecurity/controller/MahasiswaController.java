package com.deni.springsecurity.controller;

import com.deni.springsecurity.entitas.User;
import com.deni.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // mendefinisikan bahwa ini adalah class controller
@RequestMapping("/mahasiswa")
public class MahasiswaController {


       @Autowired
        private UserRepository userRepository;
        @GetMapping("/new")
        public String newMahasiswa(Authentication auth){
            // username login menggunakan controller yang ngambil data dari database
            User userLogin = userRepository.findByName(auth.getName());
            // didalame username itu ada role apa aja?
            auth.getAuthorities();
            return "/mahasiswa/TambahMahasiswa";
        }
}
