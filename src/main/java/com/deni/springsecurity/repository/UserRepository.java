package com.deni.springsecurity.repository;

import com.deni.springsecurity.entitas.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    User findByName(String name);
}
