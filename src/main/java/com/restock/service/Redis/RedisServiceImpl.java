package com.restock.service.Redis;

import com.restock.domain.product.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private final RedisTemplate<String, Map<String, Object>> redisTemplate;
    private static final String REDIS_KEY_REVIEW = "product:";
    @Override
    public void saveList(Product product, Long userId) { //레디스에 정보저장
        String key = REDIS_KEY_REVIEW + product.getId();
        String userKey = "user:" + userId;

        Map<String, Object> values = new HashMap<>();
        values.put("restockCount", String.valueOf(product.getRestockCount()));
        values.put("sendAt", LocalDateTime.now().format(formatter));

        redisTemplate.opsForHash().putAll(key + ":" + userKey, values);

    }


    @Override
    public int getRestockCount(Long productId,Long userId) {
        String key = REDIS_KEY_REVIEW + productId;
        String userKey = "user:" + userId;
        Object restockCount = redisTemplate.opsForHash().get(key + ":" + userKey, "restockCount");
        return restockCount != null ? Integer.parseInt(restockCount.toString()) : 0;
    }

    @Override
    public LocalDateTime getSendAt(Long productId,Long userId) {
        String key = REDIS_KEY_REVIEW + productId;
        String userKey = "user:" + userId;
        Object sendAt = redisTemplate.opsForHash().get(key + ":" + userKey, "sendAt");
        return sendAt != null ? LocalDateTime.parse(sendAt.toString()) : null;
    }
}
