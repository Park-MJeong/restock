package com.restock.infrastructure.productUserNotification;

import com.restock.domain.productUserNotification.ProductUserNotification;
import com.restock.domain.productUserNotification.ProductUserNotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@AllArgsConstructor
public class ProductUserNotificationRepositoryImpl implements ProductUserNotificationRepository {
    private final JpaProductUserNotificationJpaRepository repository;

    @Override
    public List<ProductUserNotification> notiList(Long productId) {
        return repository.findByProductIdAndPushStatus(productId);
    }
}
