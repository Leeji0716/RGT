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
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "store")
    private List<StoreTable> storeTables = new ArrayList<>();

    @OneToMany(mappedBy = "store")
    private List<StoreMenu> storeMenus = new ArrayList<>();

    @Builder
    public Store(String name) {
        this.name = name;
    }

    public void addStoreTable(StoreTable storeTable) {
        storeTables.add(storeTable);
        storeTable.setStore(this);
    }

    public void removeStoreTable(StoreTable storeTable) {
        storeTables.remove(storeTable);
        storeTable.setStore(null);
    }

    public void addStoreMenu(StoreMenu storeMenu) {
        storeMenus.add(storeMenu);
        storeMenu.setStore(this);
    }

    public void removeStoreMenu(StoreMenu storeMenu) {
        storeMenus.remove(storeMenu);
        storeMenu.setStore(null);
    }
}
