package com.cupcakeProject.repository;

import com.cupcakeProject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product,Integer> {
}
