package com.restock.service;

import com.esotericsoftware.minlog.Log;
import com.restock.domain.productUserNotificationHistory.ProductUserNotificationHistory;
import com.restock.domain.productUserNotificationHistory.ProductUserNotificationHistoryRepository;
import com.restock.service.Redis.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class MysqlService {
    private final RedisService redisService;
    private final ProductUserNotificationHistoryRepository productUserNotificationHistoryRepository;
    private final RedisTemplate<String, Map<String, Object>> redisTemplate;
    private static final String REDIS_FAILED_KEYS = "failed";

    @Transactional
    public void saveData(Set<String> keys){
        List<ProductUserNotificationHistory> historyList;
        historyList= getAllRedisDate(keys);
        Log.info(Arrays.toString(historyList.toArray()));
        productUserNotificationHistoryRepository.saveAll(historyList);  //레디스값 모두 sql테이블에 저장
        keys.stream()
                .filter(Objects::nonNull)
                .forEach(key -> {
                    Boolean result = redisTemplate.delete(key); //레디스 값 삭제
                    Log.info("레디스값 삭제");
                    if (Boolean.FALSE.equals(result)) { // 삭제 실패
                        // 삭제 실패 시 추가 데이터를 저장
                        Map<Object, Object> failedData = redisTemplate.opsForHash().entries(key);
                        if (!failedData.isEmpty()) {
                            // 실패 데이터를 REDIS_FAILED_KEYS에 저장
                            redisTemplate.opsForHash().putAll(REDIS_FAILED_KEYS + ":" + key, failedData);
                        }
                    }
                });
    }

    private List<ProductUserNotificationHistory> getAllRedisDate(Set<String> keys){
        List<ProductUserNotificationHistory> historyList = new ArrayList<>();
        for (String key : keys) {
            String[] parts = key.split(":");  // ':'을 기준으로 분리

            if (parts.length == 4) {
                String productId = parts[1];  // productId
                String userId = parts[3];     // userId
                historyList.add(createHistory(Long.parseLong(productId),Long.parseLong(userId)));
            }
        }
        return historyList;
    }

    private ProductUserNotificationHistory createHistory(Long productId,Long userId){
        ProductUserNotificationHistory history = new ProductUserNotificationHistory();
        int stockCount=redisService.getRestockCount(productId,userId);
        LocalDateTime sendAt=redisService.getSendAt(productId,userId);

        history.setProductId(productId);
        history.setUserId(userId);
        history.setRestockCount(stockCount);
        history.setSendAt(sendAt);
        return history;

    }

    @Scheduled(fixedRate = 600000) // 1분마다 실패한 키 재시도
    public void retryFailedKeys() {
        Set<String> failedKeys = redisTemplate.keys(REDIS_FAILED_KEYS + ":*");
        if (failedKeys != null) {
            for (String failedKey : failedKeys) {
                // 실패한 데이터 가져오기
                Map<Object, Object> failedData = redisTemplate.opsForHash().entries(failedKey);
                if (!failedData.isEmpty()) {
                    // 원래 삭제 대상 Redis 키 복원
                    String originalKey = failedKey.replace(REDIS_FAILED_KEYS + ":", "");
                    // 원래 키 삭제 재시도
                    Boolean result = redisTemplate.delete(originalKey);

                    if (Boolean.TRUE.equals(result)) {
                        // 삭제 성공 시 실패 데이터 제거
                        redisTemplate.delete(failedKey);
                    } else {
                        Log.info("삭제실패 " + originalKey );
                    }
                }
            }
        }
    }


}
