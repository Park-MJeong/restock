package com.restock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "ProductUserNotificationHistory")
public class ProductUserNotificationHistory {
    @Id
    @Generated
    private Long id;
    private Long productId;
    private Long userId;
    private int restockCount;
    private LocalDateTime sendAt;
}
