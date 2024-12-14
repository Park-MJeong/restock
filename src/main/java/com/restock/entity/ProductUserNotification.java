package com.restock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "ProductUserNotification")
public class ProductUserNotification {
    @Id
    @GeneratedValue
    private Long id;
    private Long productId;
    private Long userId;
    private String pushStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



}
