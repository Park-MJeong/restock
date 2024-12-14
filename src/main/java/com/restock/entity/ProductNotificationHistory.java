package com.restock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity(name = "ProductNotificationHistory")
public class ProductNotificationHistory {

    @Id
    @GeneratedValue
    private Long id;

    private Long productId;
    private int restockCount;
    private String notiStatus;
    private Long userId;
}
