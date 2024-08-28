package com.example.RGT.Entity;

import com.example.RGT.ETC.Status;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class MyOrder {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Long totalPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime orderTime;

    @OneToMany(mappedBy = "myOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @Builder
    public MyOrder(Status status){
        this.status = status;
        this.orderTime = LocalDateTime.now();
    }

    public void addOrderMenu(OrderMenu orderMenu) {
        orderMenus.add(orderMenu);
        orderMenu.setMyOrder(this);
    }

    public void removeOrderMenu(OrderMenu orderMenu) {
        orderMenus.remove(orderMenu);
        orderMenu.setMyOrder(null);
    }
}
