package com.restock.infrastructure.productNotificationHistory;

import com.restock.domain.productNotificationHistory.ProductNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaProductNotificationHistoryRepository extends JpaRepository<ProductNotificationHistory, Long> {
    @Modifying
    @Query("""
           update ProductNotificationHistory pnh
           set pnh.notiStatus = :notistatus,pnh.userId= :userId
           where pnh.productId = :productId and pnh.restockCount = :restockCount
           """)
    void updateNotiStatus(@Param("notistatus")String notistatus, @Param("productId")Long productId,@Param("restockCount") int restockCount,@Param("userId") Long userId);
}
