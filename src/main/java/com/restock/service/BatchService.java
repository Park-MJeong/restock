package com.restock.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchService {
    private final RedisTemplate<String, Map<String, Object>> redisTemplate;
    private final MysqlService mysqlService;
    private static final String REDIS_KEY_REVIEW = "product:";

    @Scheduled(fixedRate = 1800) //레디스값 주기적으로 db에 저장
    public void transferDateToMySQL(){
        Set<String> keys = redisTemplate.keys(REDIS_KEY_REVIEW + "*");
        if(keys != null && !keys.isEmpty()){
            log.info("db로저장");

            mysqlService.saveData(keys);

        }
    }

}
