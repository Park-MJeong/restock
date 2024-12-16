package com.restock.infrastructure.productNotificationHistory;

import com.restock.domain.product.Product;
import com.restock.domain.productNotificationHistory.ProductNotificationHistory;
import com.restock.domain.productNotificationHistory.ProductNotificationHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ProductNotificationHistoryRepositoryImpl implements ProductNotificationHistoryRepository {
    private final JpaProductNotificationHistoryRepository repository;
    @Override
    public void save(ProductNotificationHistory productNotificationHistory) {
        repository.save(productNotificationHistory);
    }

    @Override
    public void notiStatus(ProductNotificationHistory history) {
        String status = history.getNotiStatus();
        long productId = history.getProductId();
        long restockCount = history.getRestockCount();
        long userId = history.getUserId();
        repository.updateNotiStatus(status,productId,restockCount,userId);
    }


}
