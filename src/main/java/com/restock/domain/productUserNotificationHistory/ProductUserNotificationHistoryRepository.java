package com.restock.domain.productUserNotificationHistory;

import java.util.List;

public interface ProductUserNotificationHistoryRepository {
    void saveAll(List<ProductUserNotificationHistory> historyList);
}
