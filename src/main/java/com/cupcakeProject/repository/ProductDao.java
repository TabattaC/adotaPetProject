package com.cupcakeProject.repository;

import com.cupcakeProject.model.Product;
import com.cupcakeProject.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product,Integer> {
    List<ProductWrapper> getAllProduct();
}
