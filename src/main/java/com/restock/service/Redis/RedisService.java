package com.restock.service.Redis;

import com.restock.domain.product.Product;

import java.time.LocalDateTime;

public interface RedisService {
    void saveList(Product product,Long userId);

//    long getProductId(Long productId,Long userId);
//    long getUserId(Long productId,Long userId);

    int getRestockCount(Long productId,Long userId);
    LocalDateTime getSendAt(Long productId,Long userId);



}
