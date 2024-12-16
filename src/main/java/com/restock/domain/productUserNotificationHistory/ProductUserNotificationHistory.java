package com.restock.domain.productUserNotificationHistory;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ProductUserNotificationHistory")
public class ProductUserNotificationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Long userId;
    private int restockCount;

    private LocalDateTime sendAt;

    public ProductUserNotificationHistory(Long productId, Long userId, int restockCount,LocalDateTime sendAt) {

        this.productId = productId;
        this.userId = userId;
        this.restockCount = restockCount;
        this.sendAt = sendAt;
    }

}
