package com.restock.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "Product")
public class Product {
    @Id
    @GeneratedValue
    private long id;

    private int restockCount;
    private long quantity;

    public Product(long id,int restockCount,long quantity) {
        this.id = id;
        this.restockCount = restockCount;
        this.quantity = quantity;
    }

}
