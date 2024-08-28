package com.example.RGT.Repository;

import com.example.RGT.Entity.Cart;
import com.example.RGT.Entity.StoreMenu;
import com.example.RGT.Entity.StoreTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByStoreTable(StoreTable storeTable);

    Optional<Cart> findByStoreTableAndStoreMenu(StoreTable storeTable, StoreMenu storeMenu);
}
