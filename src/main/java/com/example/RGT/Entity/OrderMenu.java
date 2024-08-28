package com.example.RGT.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class OrderMenu {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String menuName;

    private Long price;

    private Long count;

    private Long total;

    @ManyToOne
    @JoinColumn(name = "my_order_id") // 외래 키를 매핑합니다
    private MyOrder myOrder;

    @Builder
    public OrderMenu(String menuName, Long price, Long count, Long total, MyOrder myOrder){
        this.menuName = menuName;
        this.price = price;
        this.count = count;
        this.total = total;
        this.myOrder = myOrder;
    }
}
