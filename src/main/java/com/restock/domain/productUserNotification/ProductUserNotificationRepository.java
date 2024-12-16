package com.restock.domain.productUserNotification;

import java.util.List;

public interface ProductUserNotificationRepository {
    List<ProductUserNotification> notiList(Long productId);
}
