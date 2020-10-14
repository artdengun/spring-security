### SPRING SECURITY

- Dependencies  dan tipe springboot yang digunakan : 
```properties
    spring 2.2.10.RELEASE /* versi springboot yang saya gunakan */
    java 8  /* saya menggunakan java 8 pada development saat ini  */
    spring-boot-starter-data-jpa /* untuk bagian koneksi dengan database */
    spring-boot-starter-security /*  untul security */
    spring-boot-starter-thymeleaf /* template engine */
    spring-boot-devtools /* untuk development tools */
    lombok /*  membantu untuk setter dan getter  */
    postgresql /* database, saya menggunakan docker */
    spring-boot-starter-actuator /* untuk actuator */
```

- Tahapan Pembuatan 
    - 
    
1. Buat database di postgresql 
2. setup application.properties
```properties

#setup connection

# untuk koneksi driver ke database postgresql
spring.datasource.driver-class-name=org.postgresql.Driver
# nama username di database saya, disesuaikan dengan 
# acces ke database temen temen
spring.datasource.username = root
# password ke database postgresql 
spring.datasource.password = ''
# url koneksi ke database, disini saya menggunakan
# nama database security_app 
spring.datasource.url = jdbc:postgresql://localhost:5432/security_app


# setup jpa

# karna kita menggunakan postgresql, maka kita masukan postgresql sebagai database 
spring.jpa.database=postgresql
# agar mengenerate secara otomatis ketika kita membuat table di entity
spring.jpa.generate-ddl=true
# akan dibuatkan table secara otomatis, dan jika ada data baru
# maka akan di sesuaikan dengan data yang baru masuk 
spring.jpa.hibernate.ddl-auto=update
# memudahkan untuk lihat log sql 
spring.jpa.show-sql=true


# setup thymeleaf

# ketika thymeleaf kita pasang di hmtml
# cache kita beri false agar setiap perbubah secara otomatis
# di update ketika datatnya berubah
spring.thymeleaf.cache=false
#  Whether to enable Thymeleaf view resolution for Web frameworks
spring.thymeleaf.enabled=true
# kita set thymeleaf untuk menggunakan mode HTML5 
spring.thymeleaf.mode=HTML5
# kita set thmeleaf untuk encoding UTF-8
spring.thymeleaf.encoding=UTF-8

```
3 . buat Entitas #  pendefinisian mapping class yang akan diubah ke dalam tabel pada database oleh framework hibernate, dalam penerapannya biasanya satu class merepresentasikan satu tabel.

```java
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
    private Integer id; // ini adalah primary key
    private String nim;
    private String nama;
    private Integer semester;
}
```

 4 .  repository # Fungsi repository adalah untuk melakukan query terhadap basis data. 

```java

package com.deni.springsecurity.repository;

import com.deni.springsecurity.entitas.Mahasiswa;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository // merepresentasikan kelas repository
public interface MahasiswaRepository  extends PagingAndSortingRepository<Mahasiswa, Integer> {
/* 
biasanya pada kelas repository kita menggunakan beberapa class seperti : 
CrudRepository terutama menyediakan fungsi CRUD.
PagingAndSortingRepository menyediakan metode untuk melakukan pagination dan menyortir catatan.
JpaRepository menyediakan beberapa metode terkait JPA 
pada kali ini kita menggunakan pagging dan sorting
pagging and sorting -> menyediakan metode untuk melakukan pagination dan menyortir catatan.
dengan <Mahasiswa, Integer> -> nama kelas dan nama dari primary key  

*/

}
```

5 . Controller # Controller diibaratkan adalah pintu masuk request dari pengguna, dalam controller bertindak meneruskan request dari pengguna atau mengembalikan kembali ke pengguna.

```java
package com.deni.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // mendefinisikan bahwa ini adalah class controller
@RequestMapping("/mahasiswa")
public class MahasiswaController { // nama kelas 

        @GetMapping("/new") // get url jadi nanti seperti ini http://localhost:8080/mahasiswa/new
        public String newMahasiswa(){  // method baru dengan nama newMahasiswa
            return "/mahasiswa/TambahMahasiswa"; // kemudian kire return ke class TambahMahasiswa
        // urlnya jadi sperti ini http://localhost:8080/mahasiswa/TambahMahasiswa
        }
}

```
6 . Buat Package templates/mahasiswa/TambahMahasiswa.html
```html
<html xmlns:th="http://www.thymeleaf.org"> <!--  untuk set thymeleaf  -->
<head>
    <meta charset="UTF-8"> <!-- tipe encoding  -->
    <title>Tambah Mahasiswa</title>
</head>
<body>
<form action=""> <!-- kita belum memberikan action pada form ini -->
    <div>
        <label for="nim">NIM</label> // label dengan nama 
        <input type="text" name="nim" id="nim" /> // id dengan nim for dan id ini saling berelasi 
    </div>
    <div>
        <label for="nama">Nama</label> // label saling berelasi
        <input type="text" name="nama" id="nama" /> // id dengan nama saling berlasi
    </div>
    <div>
        <label for="semester">Semester</label> // label dengan nama semester
        <select name="semester" id="semester" > // for berelasi dengan id ini 
        <option th:each="i : ${#numbers.sequence( 1, 12) }" value="1" th:value="${i}">Semester 
<span th:text="${i}">1</span></option> <!-- untuk menampilkan teks secara berulang  -->
        <!-- kita melakikan pengulanngan dari 1 sampai 12   -->
        </select>
    </div>
    <div>
        <buttton type="submit">Kirim</buttton> <!-- ketika di klik maka dia menjalankan perintah submit, 
 dia akan menjalankan perintah yang ada di <form action=""> untuk melakukan proses selanjutnya     
 -->
    </div>

</form>

</body>
</html>

```

7 . Security Configurer Adapter

```java
package com.deni.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // merepresentasikan kelas konfigurasi
@EnableWebSecurity // kita mengenable web security agar konfigurasinya bisa kita gunakan
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // kita extendes 

    @Override //  setiap class yang mengimplement interface kelas tersebut harus di override untuk mengimplementasikan method
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication() // kita menyimpan data dalam memory. artinya cahce browser kita 
                .passwordEncoder(passwordEncoder()) // nah disini kita menset untuk menggunakan pengamanan 
// BCryptPasswordEncoder ( 1 arah ) artinya datanya tidak bisa di ubah ke text, dan akan selalu berbeda 
// jadi sekali tersimpan di database. method ini sangat aman 
                .withUser("user")  // nama user 
                .password(passwordEncoder().encode("password")) // passwordnya kita encode
                .roles("USER"); // ini rolesnya sebagai user 
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests() // a
                .antMatchers("/mahasiwa/new").hasRole("OPERATOR") 
                // antMatchers artinya link mana aja nih yang bisa di akses oleh si OPERATOR, atau hasRole sesuai yang kita atur nantinya 
                .antMatchers("daftar/new").access("hasRole('admin') or hasRole('OPERATOR')") // yang bisa akses link si admin atau si operator nilainya harus true salah satunya
                .antMatchers("daftar/edit").access("hasRole('admin') and hasRole('OPERATOR')") // yang bisa akses link si admin atau si operator  artinya nilainy harus true dua duanya 
                .anyRequest().authenticated() 
                .and().formLogin().permitAll() 
                .and().logout().logoutSuccessUrl("/home").permitAll();
                // ketika user sudah logout , dia akan di redicer ke home 
                // untuk logout sendiri sudah diatur oleh si spring security
                // jadi bisa kita langsung gunakan 
    }

    @Bean // fungsinya untuk  menginstance sebuah class
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        // agar si BCryptPasswordEncoder methodnya kita bisa gunakan
    }



}

```

kita buat user schema , untuk kita migrate table ke database, tidak menggunakan inMemoryDatabase atau cache di browser

8 . dipackage entity kita buat  class dengan nama  User
    
```java

package com.deni.springsecurity.entitas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data // mendefinisikan lombok
@NoArgsConstructor  // no arguman 
@AllArgsConstructor // all argumen
@Entity // mendefinisikan pembuatan table di database
@Table(name = "s_user") // membuat tabel dengagn nama s_user di database 
public class User {

    @Id // wajib ada karean merepresentasikan primary key 
    @GeneratedValue // agar nilai yang ada dibawah di generated atau update secara otomatis
    private Integer id; // id ini primary key 
    private String name; // nama 
    private String password; // password 
    private boolean active; // menandakan user yang active atau tidak 

    @OneToMany(mappedBy = "user") // relasi satu ke banyak yang mapped  user
    private List<UserRoles> roles = new ArrayList<>();  // kemudian UserROlesnya dimasukan ke dalam array list, 
}

```

9 . Role 

```java
package com.deni.springsecurity.entitas;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data // mendefinisikan lomnok
@NoArgsConstructor // no argumen
@AllArgsConstructor // all argumen
@Entity // merepresentasikan pembuatan table didatabase
@Table(name = "s_Role") // nama table di database
public class Role { // clasnyaa bernama role 
 
    @Id // wajib ada karna merepresentasikan primary key 
    @GeneratedValue // nilainya di generated otomatis ketika berubah , ini adalah identifier 
    private Integer id; // primary key 
    private String nama;// nama 
    private String password; // passworrd

    @OneToMany(mappedBy = "role") // relasi satu ke banyak mapped role 
    private List<UserRoles> list = new ArrayList<>(); // menampung nilanya dalam list , relasi berpusat di UserRoles
    // yang menampung nilai
}

```

9 . userRoles

```java
package com.deni.springsecurity.entitas;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data // generate setter dan getter 
@NoArgsConstructor // agar sudah include argumen 
@AllArgsConstructor // all argumen sudah include
@Entity // untuk membuat table
@Table(name = "s_user_roles") // untuk membuat table dengan nama s_user_roles
public class UserRoles {

    @Id // merepresentasikan primary key
    @GeneratedValue // agar nilainya tergenerate secara otomatis saat data berubah atau termodif 
    private Integer id;

    @ManyToOne // banyak ke satu ini merepresentasikan si User 
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToOne // banyak ke satu ini merepresentasikan si Role
    @JoinColumn(name = "id_role", nullable = false)
    private Role role;

}

```

10. user repository 


```java
package com.deni.springsecurity.repository;

import com.deni.springsecurity.entitas.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository // merepresentasikan repository 
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    User findByName(String name); // find by nama user 
}

```

11 . user principal


```java


package com.deni.springsecurity.entitas;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails { // implements si userDetails 
// untuk bisa menggunakan Authorities

    private User user; // kita masukan data User 

    public UserPrincipal(User user) { // si principal punya paramater class User dan object User. 
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> listRole = new ArrayList<>(); // nilainya kita tampung di array list 
        this.user.getRoles().forEach((ur) -> { // kita merepresentasikan si user kemudian kita beli perulangan 
            listRole.add(new SimpleGrantedAuthority(ur.getRole().getNama()));
            // ketika syarat sudah terpenuhi dia akan meng getNama
        });
        return listRole;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }
}
```

12 . user service

```java

package com.deni.springsecurity.service;

import com.deni.springsecurity.entitas.User;
import com.deni.springsecurity.entitas.UserPrincipal;
import com.deni.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {


        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
                User user = userRepository.findByName(s);
                if(user != null)
                        return new UserPrincipal(user);
        else return new UserPrincipal(new User());
    }
}

```

13 . tambahkan depedency baru 


```properties
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true
```

14 . buat dummy database di postgre kita 

15 . disable in memory agar datanya ke simpna di database 

```java
package com.deni.springsecurity.config;

import com.deni.springsecurity.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder())
//                .withUser("user")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER");
//    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/mahasiwa/new").hasRole("OPERATOR")
                .antMatchers("/daftar/new").access("hasRole('ADMIN') and hasRole('OPERATOR')") // OR salah satu and dua duanya
                .anyRequest().authenticated()
                .and().formLogin().permitAll()
                .and().logout().logoutSuccessUrl( "/home").permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService){
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(userService);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;

    }
}

``` 

16 . tambahkan index controller

```java

package com.deni.springsecurity.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

        @GetMapping("/home")
        public String indexHtml() {
                return "/index";
            }
}


```

17 . tambahkan pom 
```xml

<dependency>
			<groupId>org.thymeleaf.extras</groupId>
			<artifactId>thymeleaf-extras-springsecurity5</artifactId>
</dependency>

```

18 . tambahkan index html 


```html
<html xmlns="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <meta charset="UTF-8">
    <title>Home Application </title>
</head>
<body>
Halaman Utama

<div sec:authorize="isAuthenticated()">
    <!-- tampil yang udah login -->
    Nama user yang login: <p sec:authentication="name">User</p><br />
    Rolenya " <p sec:authentication="authorities">ROLE_USER</p>
</div>
</body>
</html>
```

20 . modif mahasiswa controller menjadi seperti ini 

```java
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

```

21 . ubah web security menjadi seperti ini 


```java


package com.deni.springsecurity.config;

import com.deni.springsecurity.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder())
//                .withUser("user")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER");
//    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/mahasiwa/new").hasRole("OPERATOR")
                .antMatchers("/daftar/new").access("hasRole('ADMIN') and hasRole('OPERATOR')") // OR salah satu and dua duanya
                .anyRequest().authenticated()
                .and().formLogin().defaultSuccessUrl("/home").permitAll()
                .and().logout().logoutSuccessUrl("/home").permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService){
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(userService);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;

    }
}

```
22. buat relasi di database 
