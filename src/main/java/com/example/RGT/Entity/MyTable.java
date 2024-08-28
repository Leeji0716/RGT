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
public class MyTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tableNumber;

    @OneToMany(mappedBy = "myTable")
    private List<StoreTable> storeTables = new ArrayList<>();

    @Builder
    public MyTable(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public void addStoreTable(StoreTable storeTable) {
        storeTables.add(storeTable);
        storeTable.setMyTable(this);
    }

    public void removeStoreTable(StoreTable storeTable) {
        storeTables.remove(storeTable);
        storeTable.setMyTable(null);
    }
}
