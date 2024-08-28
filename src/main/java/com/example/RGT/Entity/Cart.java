package com.example.RGT.Entity;

import com.example.RGT.ETC.Status;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long count;

    @ManyToOne
    @JoinColumn(name = "store_menu_id")
    private StoreMenu storeMenu;

    @ManyToOne
    @JoinColumn(name = "store_table_id")
    private StoreTable storeTable;

    @Builder
    public Cart(Long count, StoreMenu storeMenu, StoreTable storeTable) {
        this.count = count;
        this.storeMenu = storeMenu;
        this.storeTable = storeTable;
    }
}
