package com.restock.infrastructure.product;

import com.restock.domain.product.Product;
import com.restock.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final JpaProductRepository jpaProductRepository;

    @Override
    public Product getProduct(Long id) {
        return jpaProductRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("제품이 존재하지 않습니다."));
    }

    @Override
    public void save(Product product) {
        jpaProductRepository.save(product);
    }


}
