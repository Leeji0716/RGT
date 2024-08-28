package com.example.RGT.Service.Module;

import com.example.RGT.DTO.MyTableResponseDTO;
import com.example.RGT.Entity.MyTable;
import com.example.RGT.Repository.MyTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyTableService {
    private final MyTableRepository tableRepository;

    public MyTable getTable(Long id) throws Exception {
        return tableRepository.findById(id).orElseThrow (() -> new Exception ("table not found"));
    }

    public List<MyTable> getAllTable() {
        return tableRepository.findAll();
    }

    public MyTable save(MyTable table) {
        return tableRepository.save(table);
    }

    private MyTableResponseDTO getTableDTO(MyTable table){
        return MyTableResponseDTO.builder()
                .id(table.getId())
                .tableNumber(table.getTableNumber())
                .build();
    }

    public List<MyTableResponseDTO> getMyTableDTOList(List<MyTable> tableList) {
        List<MyTableResponseDTO> myTableResponseDTOList = new ArrayList<>();
        for (MyTable myTable : tableList){
            MyTableResponseDTO tableResponseDTO = getTableDTO(myTable);
            myTableResponseDTOList.add(tableResponseDTO);
        }
        return myTableResponseDTOList;
    }
}
