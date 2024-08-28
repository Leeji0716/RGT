package com.example.RGT.Service.Module;

import com.example.RGT.DTO.MenuResponseDTO;
import com.example.RGT.DTO.StoreResponseDTO;
import com.example.RGT.DTO.StoreTableResponseDTO;
import com.example.RGT.Entity.Store;
import com.example.RGT.Entity.StoreMenu;
import com.example.RGT.Repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    public Store save(Store store) {
        return storeRepository.save(store);
    }

    public StoreResponseDTO storeDTO (Store store, List<StoreTableResponseDTO> storeTableResponseDTOList){
        return StoreResponseDTO.builder()
                .id(store.getId())
                .name(store.getName())
                .storeTablesDTO(storeTableResponseDTOList)
                .build();
    }

    public Store getStore(Long id) throws Exception{
        return storeRepository.findById(id).orElseThrow (() -> new Exception ("store not found"));
    }
}
