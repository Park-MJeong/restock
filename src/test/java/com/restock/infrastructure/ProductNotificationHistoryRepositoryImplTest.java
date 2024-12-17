package com.restock.infrastructure;

import com.restock.domain.productNotificationHistory.ProductNotificationHistory;
import com.restock.infrastructure.productNotificationHistory.JpaProductNotificationHistoryRepository;
import com.restock.infrastructure.productNotificationHistory.ProductNotificationHistoryRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ProductNotificationHistoryRepositoryImplTest {
    @Mock
    private JpaProductNotificationHistoryRepository jpaRepository;

    @InjectMocks
    private ProductNotificationHistoryRepositoryImpl repository;

    private ProductNotificationHistory history;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        history = new ProductNotificationHistory();
        history.setNotiStatus("Y");
        history.setProductId(1L);
        history.setRestockCount(5);
        history.setUserId(100L);

    }

    @Test
    @DisplayName("테이블정보저장")
    void save() {
        repository.save(history);
        verify(jpaRepository,times(1)).save(history);
    }

    @Test
    @DisplayName("상품별 재입고 알림 히스토리 상태변경")
    void notiStatus() {
        // 전달된 인자값
        ArgumentCaptor<String> statusCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Long> productIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Integer> restockCountCaptor = ArgumentCaptor.forClass(int.class);
        ArgumentCaptor<Long> userIdCaptor = ArgumentCaptor.forClass(Long.class);


        repository.notiStatus(history);


        verify(jpaRepository, times(1)).updateNotiStatus(
                statusCaptor.capture(),
                productIdCaptor.capture(),
                restockCountCaptor.capture(),
                userIdCaptor.capture()
        );


        assertEquals(history.getNotiStatus(), statusCaptor.getValue());
        assertEquals(history.getProductId(), productIdCaptor.getValue());
        assertEquals(history.getRestockCount(), restockCountCaptor.getValue());
        assertEquals(history.getUserId(), userIdCaptor.getValue());
    }
}