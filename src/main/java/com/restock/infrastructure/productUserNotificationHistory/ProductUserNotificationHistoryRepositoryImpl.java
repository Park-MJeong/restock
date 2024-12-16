package com.restock.infrastructure.productUserNotificationHistory;

import com.restock.domain.product.Product;
import com.restock.domain.productUserNotificationHistory.ProductUserNotificationHistory;
import com.restock.domain.productUserNotificationHistory.ProductUserNotificationHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ProductUserNotificationHistoryRepositoryImpl implements ProductUserNotificationHistoryRepository {
    private final JpaProductUserNotificationHistoryRepository repository;
    @Override
    public void saveAll(List<ProductUserNotificationHistory> historyList) {
        repository.saveAll(historyList);
    }
}
