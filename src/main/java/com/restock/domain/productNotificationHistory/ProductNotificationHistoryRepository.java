package com.restock.domain.productNotificationHistory;


public interface ProductNotificationHistoryRepository {
    void save(ProductNotificationHistory productNotificationHistory);
    void notiStatus(ProductNotificationHistory productNotificationHistory);
}
