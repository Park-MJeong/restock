package com.restock.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Map;


@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;
    @Value(("${spring.data.redis.password}"))
    private String password;
    //
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory(host, port);
//    }
//    @Bean
//    public RedissonClient redissonClient() {
//        Config config = new Config();
//        config.useSingleServer()
//                .setAddress("redis://127.0.0.1:6379")  // Redis 서버 주소
//                .setPassword(password)                // Redis 비밀번호 (없다면 생략)
//                .setConnectionPoolSize(1000)          // 연결 풀 크기
//                .setConnectionMinimumIdleSize(100)    // 최소 유휴 연결 수
//                .setIdleConnectionTimeout(10000)      // 유휴 연결 타임아웃 (ms)
//                .setConnectTimeout(10000)             // 연결 타임아웃 (ms)
//                .setRetryAttempts(3)                  // 재시도 횟수
//                .setRetryInterval(1500)             // 재시도 간격 (ms)
//                .setTimeout(10000); // 응답 타임아웃 10초
//
//        // 락 기본 타임아웃 설정 (예: 60초)
//        config.setLockWatchdogTimeout(60000);
//
//        return Redisson.create(config);
//    }


    @Bean
    public RedisTemplate<String, Map<String, Object>> redisTemplate(
            RedisConnectionFactory connectionFactory)  {
        RedisTemplate<String,Map<String, Object>> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template; //객체로 등록
    }




}
