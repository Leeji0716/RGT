package com.example.RGT.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private Long price;

    private String content;

    @OneToMany(mappedBy = "menu")
    private List<StoreMenu> storeMenus = new ArrayList<>();

    @Builder
    public Menu(String name, Long price, String content) {
        this.name = name;
        this.price = price;
        this.content = content;
    }

    public void addStoreMenu(StoreMenu storeMenu) {
        storeMenus.add(storeMenu);
        storeMenu.setMenu(this);
    }

    public void removeStoreMenu(StoreMenu storeMenu) {
        storeMenus.remove(storeMenu);
        storeMenu.setMenu(null);
    }
}
