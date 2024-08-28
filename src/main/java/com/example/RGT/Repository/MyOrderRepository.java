package com.example.RGT.Repository;

import com.example.RGT.Entity.MyOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyOrderRepository extends JpaRepository<MyOrder, Long> {
}
