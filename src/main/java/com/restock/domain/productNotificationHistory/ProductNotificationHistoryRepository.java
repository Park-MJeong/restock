package com.restock.domain.productNotificationHistory;

import com.restock.domain.product.Product;

public interface ProductNotificationHistoryRepository {
    void save(ProductNotificationHistory productNotificationHistory);
    void notiStatus(ProductNotificationHistory productNotificationHistory);
}
