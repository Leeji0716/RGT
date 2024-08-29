package com.example.RGT.Repository;

import com.example.RGT.Entity.MyTable;
import com.example.RGT.Entity.Store;
import com.example.RGT.Entity.StoreTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreTableRepository extends JpaRepository<StoreTable, Long> {
    List<StoreTable> findByStore(Store store);

    StoreTable findByStoreAndMyTable(Store store, MyTable myTable);
}
