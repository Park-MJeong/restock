package com.restock.domain;

import com.restock.domain.product.Product;
import com.restock.domain.productNotificationHistory.ProductNotificationHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductNotificationHistoryTest {
    private Product product;
    private ProductNotificationHistory history;
    private Long userId;
    @BeforeEach
    void setUp() {
        product = new Product(1L, 1, 5L);
        userId = 5L;

    }

    @Test
    @DisplayName("제품테이블에 재고가 들어왔을때")
    void inProgress() {
        history = ProductNotificationHistory.inProgress(product);
        assertEquals(1L, history.getProductId());
        assertEquals(1, history.getRestockCount());
        assertEquals("IN_PROGRESS", history.getNotiStatus());
        assertNull(history.getUserId());
    }

    @Test
    @DisplayName("생성자테스트")
    void testConstructor(){
        history= new ProductNotificationHistory(product);
        assertEquals(1L, history.getProductId());
        assertEquals(1, history.getRestockCount());
        assertEquals("IN_PROGRESS", history.getNotiStatus());
    }
    @Test
    @DisplayName("제품이없을때")
    void cancelBySoldOut() {
        history = new ProductNotificationHistory(product);
        history.cancelBySoldOut(userId);
        assertEquals(1L, history.getProductId());
        assertEquals(1, history.getRestockCount());
        assertEquals("CANCELED_BY_SOLD_OUT", history.getNotiStatus());
        assertEquals(5L,history.getUserId());
    }

    @Test
    @DisplayName("에러발생")
    void cancelByError() {
        history = new ProductNotificationHistory(product);
        history.cancelByError(userId);
        assertEquals(1L, history.getProductId());
        assertEquals(1, history.getRestockCount());
        assertEquals("CANCELED_BY_ERROR", history.getNotiStatus());
        assertEquals(5L,history.getUserId());
    }

    @Test
    @DisplayName("모두완료")
    void completed() {
        history = new ProductNotificationHistory(product);
        history.completed(userId);
        assertEquals(1L, history.getProductId());
        assertEquals(1, history.getRestockCount());
        assertEquals("COMPLETED", history.getNotiStatus());
        assertEquals(5L,history.getUserId());


    }
}