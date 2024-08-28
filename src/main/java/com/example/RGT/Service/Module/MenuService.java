package com.example.RGT.Service.Module;

import com.example.RGT.DTO.MenuResponseDTO;
import com.example.RGT.Entity.Menu;
import com.example.RGT.Entity.StoreTable;
import com.example.RGT.Repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    public MenuResponseDTO MenuDTO(Menu menu) {
        return MenuResponseDTO.builder()
                .id(menu.getId())
                .name(menu.getName())
                .price(menu.getPrice())
                .content(menu.getContent())
                .build();
    }

    public Menu getMenuById(Long id) throws Exception {
        Menu menu = menuRepository.findById(id).orElseThrow (() -> new Exception ("menu not found"));
        return menu;
    }

    public List<MenuResponseDTO> getMenuList(List<Menu> menuIdList) throws Exception {
        List<MenuResponseDTO> menuResponseDTOList = new ArrayList<>();

        for (Menu menu : menuIdList){
            menuResponseDTOList.add(MenuDTO(menu));
        }
        return menuResponseDTOList;
    }
}
