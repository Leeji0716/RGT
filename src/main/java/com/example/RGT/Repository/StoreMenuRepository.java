package com.example.RGT.Repository;

import com.example.RGT.Entity.Store;
import com.example.RGT.Entity.StoreMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreMenuRepository extends JpaRepository<StoreMenu, Long> {
    List<StoreMenu> findByStore(Store store);
}
