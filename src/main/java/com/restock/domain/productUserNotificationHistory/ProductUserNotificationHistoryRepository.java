package com.restock.domain.productUserNotificationHistory;

import com.restock.domain.product.Product;

import java.util.List;

public interface ProductUserNotificationHistoryRepository {
    void saveAll(List<ProductUserNotificationHistory> historyList);
}
