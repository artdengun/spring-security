### SPRING SECURITY

- Dependencies  dan tipe springboot yang digunakan : 
```properties
    spring 2.2.10.RELEASE
    java 8 
    spring-boot-starter-data-jpa
    spring-boot-starter-security
    spring-boot-starter-thymeleaf
    spring-boot-devtools
    lombok
    postgresql
    spring-boot-starter-actuator
```

- Tahapan Pembuatan 
    - 
    
1. Buat database di postgresql 
2. setup application.properties
```properties

#setup connection
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username = kpaydeveloper
spring.datasource.password = ''
spring.datasource.url = jdbc:postgresql://localhost:5432/security_app


# setup jpa

spring.jpa.database=postgresql
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


# setup thymeleaf
spring.thymeleaf.cache=false
spring.thymeleaf.enabled=true
spring.thymeleaf.mode=HTML5
spring.thymeleaf.encoding=UTF-8

```
3 . buat Entitas digunakan membuat table di database

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
    private Integer id;
    private String nim;
    private String nama;
    private Integer semester;
}
```

 4 .  repository 

```java

package com.deni.springsecurity.repository;

import com.deni.springsecurity.entitas.Mahasiswa;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MahasiswaRepository  extends PagingAndSortingRepository<Mahasiswa, Integer> {
}
```

5 . Controller

```java
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

```
6 . Buat Package templates/mahasiswa/TambahMahasiswa.html
```html
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Tambah Mahasiswa</title>
</head>
<body>
<form action="">
    <div>
        <label for="nim">NIM</label>
        <input type="text" name="nim" id="nim" />
    </div>
    <div>
        <label for="nama">Nama</label>
        <input type="text" name="nama" id="nama" />
    </div>
    <div>
        <label for="semester">Semester</label>
        <select name="semester" id="semester" >
        <option th:each="i : ${#numbers.sequence( 1, 12) }" value="1" th:value="${i}">Semester <span th:text="${i}">1</span></option>
        </select>
    </div>
    <div>
        <buttton type="submit">Kirim</buttton>
    </div>

</form>

</body>
</html>

```
    