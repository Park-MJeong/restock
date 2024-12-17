package com.restock.infrastructure.product;

import com.restock.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<Product, Long> {

}
