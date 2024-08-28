package com.example.RGT.Service.Module;

import com.example.RGT.DTO.StoreMenuResponseDTO;
import com.example.RGT.Entity.Menu;
import com.example.RGT.Entity.Store;
import com.example.RGT.Entity.StoreMenu;
import com.example.RGT.Repository.StoreMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreMenuService {
    private final StoreMenuRepository storeMenuRepository;

    public List<StoreMenuResponseDTO> getStoreMenueList(Store store) {
        List<StoreMenu> storeMenuList = storeMenuRepository.findByStore(store);
        List<StoreMenuResponseDTO> storeMenuResponseDTOList = new ArrayList<>();
        for (StoreMenu storeMenu : storeMenuList){
            storeMenuResponseDTOList.add(StoreMenuDTO(storeMenu));
        }
        return storeMenuResponseDTOList;
    }

    public StoreMenuResponseDTO StoreMenuDTO(StoreMenu storeMenu){
        return StoreMenuResponseDTO.builder()
                .id(storeMenu.getId())
                .name(storeMenu.getMenu().getName())
                .price(storeMenu.getMenu().getPrice())
                .build();
    }

    public StoreMenu create(Store store, Menu menu) {
        StoreMenu storeMenu = StoreMenu.builder()
                .store(store)
                .menu(menu)
                .build();
        return storeMenuRepository.save(storeMenu);
    }

    public StoreMenu getStoreMenu(Long storeMenuId) throws Exception{
        return storeMenuRepository.findById(storeMenuId).orElseThrow(() -> new Exception ("store menu not found"));
    }
}
