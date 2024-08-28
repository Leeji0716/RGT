package com.example.RGT.Service.Module;

import com.example.RGT.DTO.StoreTableResponseDTO;
import com.example.RGT.Entity.Store;
import com.example.RGT.Entity.StoreTable;
import com.example.RGT.Entity.MyTable;
import com.example.RGT.Repository.StoreTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreTableService {
    private final StoreTableRepository storeTableRepository;

    public StoreTable getTable(Long i) throws Exception {
        return storeTableRepository.findById(i).orElseThrow (() -> new Exception ("storeTable not found"));
    }

    public StoreTableResponseDTO StoreTableDTO(StoreTable storeTable){
        return StoreTableResponseDTO.builder()
                .id(storeTable.getId())
                .store(storeTable.getStore().getName())
                .myTable(storeTable.getMyTable().getTableNumber())
                .build();
    }

    public List<StoreTableResponseDTO> StoreTableDTOList(List<StoreTable> storeTables) {
        List<StoreTableResponseDTO> storeTableResponseDTOList = new ArrayList<>();
        for (StoreTable storeTable : storeTables){
            storeTableResponseDTOList.add(this.StoreTableDTO(storeTable));
        }
        return storeTableResponseDTOList;
    }

    public StoreTable create(Store store, MyTable myTable) {
        StoreTable storeTable = StoreTable.builder()
                .store(store)
                .myTable(myTable)
                .build();
        return storeTableRepository.save(storeTable);
    }

    public List<StoreTableResponseDTO> findTableByStore(Store store) {
        List<StoreTable> storeTables = storeTableRepository.findByStore(store);
        List<StoreTableResponseDTO> storeTableResponseDTOList = new ArrayList<>();
        for(StoreTable storeTable : storeTables){
            storeTableResponseDTOList.add(StoreTableDTO(storeTable));
        }
        return storeTableResponseDTOList;
    }
}
