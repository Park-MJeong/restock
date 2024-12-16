package com.restock.domain.product;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor()
@Entity(name = "Product")
public class Product {
    @Id
    @GeneratedValue
    private long id;

    private int restockCount;
    private long quantity;

    public Product(Long id,int restockCount, long quantity) {
        this.id = id;
        this.restockCount = restockCount;
        this.quantity = quantity;
    }

    public Product add() { //재입고시 수량10개라고 가정
        return new Product(id,restockCount+1,quantity+10);
    }
    public void decrease(){
        quantity--;
    }
    public boolean isSoldOut() {
        return this.quantity == 0L;
    }

}
