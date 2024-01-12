package com.sample.ecommers.repository;

import com.sample.ecommers.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Products, Long> {
}
