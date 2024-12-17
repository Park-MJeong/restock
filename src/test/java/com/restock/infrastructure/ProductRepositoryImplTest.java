package com.restock.infrastructure;

import com.restock.domain.product.Product;
import com.restock.infrastructure.product.JpaProductRepository;
import com.restock.infrastructure.product.ProductRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductRepositoryImplTest {

    @Mock
    private JpaProductRepository jpaRepository;

    @InjectMocks
    private ProductRepositoryImpl repository;

    private Product product;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        product = new Product(1L, 5, 10L);
    }

    @Test
    @DisplayName("상품테이블 재고입고")
    void getProduct() {

        when(jpaRepository.findById(1L)).thenReturn(Optional.of(product));


        Product foundProduct = repository.getProduct(1L);


        assertEquals(1L, foundProduct.getId());
        assertEquals(5, foundProduct.getRestockCount());
        assertEquals(10L, foundProduct.getQuantity());


        verify(jpaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("상품테이블정보저장")
    void save() {

        when(jpaRepository.save(any(Product.class))).thenReturn(product);


        repository.save(product);


        verify(jpaRepository, times(1)).save(product);
    }
}