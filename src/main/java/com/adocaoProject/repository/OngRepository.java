package com.adocaoProject.repository;

import com.adocaoProject.model.Ong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OngRepository extends JpaRepository<Ong,Long> {

    List<Ong> findByNomeContaining(String nome);
}
