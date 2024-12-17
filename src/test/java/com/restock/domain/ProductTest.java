package com.restock.domain;

import com.restock.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    private Product product;
    @BeforeEach
    void setUp() {
        product = new Product(1L, 1, 5);
    }


    @Test
    @DisplayName("제품테이블 재고횟수,수량 증가테스트")
    void add() {
        Product newProduct = product.add();
        assertEquals(product.getId(), newProduct.getId());
        assertEquals(product.getRestockCount()+1, newProduct.getRestockCount());
        assertEquals(product.getQuantity()+10, newProduct.getQuantity());
    }

    @Test
    @DisplayName("제품테이블 수량감소테스트")
    void decrease() {
        product.decrease();
        assertEquals(4, product.getQuantity());
    }

    @Test
    @DisplayName("재고가0일때 반환값테스트")
    void isSoldOut() {
        assertFalse(product.isSoldOut());

        // 재고를 0으로 만들어 품절 상태 테스트
        for (int i = 0; i < 5; i++) {
            product.decrease();
        }
        assertTrue(product.isSoldOut());
    }
}