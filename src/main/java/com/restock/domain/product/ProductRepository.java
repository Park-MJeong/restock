package com.restock.domain.product;

public interface ProductRepository {
    Product getProduct(Long id);
    void save(Product product);

}
