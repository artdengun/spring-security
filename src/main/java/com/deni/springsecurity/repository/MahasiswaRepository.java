package com.deni.springsecurity.repository;

import com.deni.springsecurity.entitas.Mahasiswa;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MahasiswaRepository  extends PagingAndSortingRepository<Mahasiswa, Integer> {
}
