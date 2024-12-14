package com.restock.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
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
