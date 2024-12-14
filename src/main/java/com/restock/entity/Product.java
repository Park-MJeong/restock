package com.restock.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity(name = "Product")
public class Product {
    @Id
    @GeneratedValue
    private long id;

    private int restockCount;
    private long quantity;

}
