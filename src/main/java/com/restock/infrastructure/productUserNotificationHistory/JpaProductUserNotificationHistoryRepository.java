package com.restock.infrastructure.productUserNotificationHistory;

import com.restock.domain.productUserNotificationHistory.ProductUserNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaProductUserNotificationHistoryRepository extends JpaRepository<ProductUserNotificationHistory, Long> {

}
