package com.restock.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
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
