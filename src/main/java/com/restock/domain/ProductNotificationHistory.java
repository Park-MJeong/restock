package com.restock.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
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
