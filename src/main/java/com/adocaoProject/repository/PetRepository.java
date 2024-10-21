package com.adocaoProject.repository;
import com.adocaoProject.model.Pets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pets,Long> {
    List<Pets> findByNomeContaining(String nome);
}