package com.restock.domain.productNotificationHistory;

import com.restock.domain.product.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "ProductNotificationHistory")
public class ProductNotificationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private int restockCount;

    private String notiStatus;

    private Long userId;


    public static ProductNotificationHistory inProgress(Product product){
        return new ProductNotificationHistory(product.getId(), product.getRestockCount(), "IN_PROGRESS",null);
    }
//    public static ProductNotificationHistory soldOut(Product product,long userId){
//        return new ProductNotificationHistory(product.getId(), product.getRestockCount(), "CANCELED_BY_SOLD_OUT");
//    }
//
//    public static ProductNotificationHistory error(Product product,long userId){
//        return new ProductNotificationHistory(product.getId(), product.getRestockCount(), "CANCELED_BY_ERROR");
//    }
//    public static ProductNotificationHistory completed(Product product,long userId){
//        return new ProductNotificationHistory(product.getId(), product.getRestockCount(), "COMPLETED");
//    }
    public ProductNotificationHistory(Product product) {
        this.productId=product.getId();
        this.restockCount=product.getRestockCount();
        this.notiStatus="IN_PROGRESS";
    }

    public void cancelBySoldOut(long userId) {
        this.notiStatus = "CANCELED_BY_SOLD_OUT";
        this.userId = userId;
    }

    public void cancelByError(long userId) {
        this.notiStatus = "CANCELED_BY_ERROR";
        this.userId = userId;
    }

    public void completed(long userId) {
        this.notiStatus = "COMPLETED";
        this.userId =  userId;
    }
    private ProductNotificationHistory(Long productId, int restockCount, String notiStatus, Long userId) {
        this.productId = productId;
        this.restockCount = restockCount;
        this.notiStatus = notiStatus;
        this.userId = userId;
    }
}
