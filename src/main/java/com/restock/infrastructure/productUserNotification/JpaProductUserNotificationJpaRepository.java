package com.restock.infrastructure.productUserNotification;

import com.restock.domain.productUserNotification.ProductUserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaProductUserNotificationJpaRepository extends JpaRepository<ProductUserNotification, Long> {
    @Query("""
           SELECT pun
           FROM ProductUserNotification pun
           WHERE pun.productId = :productId AND pun.pushStatus = 'Y'
           ORDER BY pun.updatedAt ASC
           """)
    List<ProductUserNotification> findByProductIdAndPushStatus(@Param("productId") Long productId);
}
