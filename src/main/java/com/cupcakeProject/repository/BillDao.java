package com.cupcakeProject.repository;

import com.cupcakeProject.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDao extends JpaRepository<Bill,Integer> {
}
