package com.example.RGT.Repository;

import com.example.RGT.Entity.MyTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyTableRepository extends JpaRepository<MyTable, Long> {
}
