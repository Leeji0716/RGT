package com.example.RGT.Service.Module;

import com.example.RGT.DTO.OrderMenuDTO;
import com.example.RGT.Entity.OrderMenu;
import com.example.RGT.Repository.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderMenuService {
    private final OrderMenuRepository orderMenuRepository;

    public List<OrderMenuDTO> OrderMenuDTOList(List<OrderMenu> orderMenus) {
        List<OrderMenuDTO> orderMenuDTOList = new ArrayList<>();
        for (OrderMenu orderMenu : orderMenus){
            orderMenuDTOList.add(OrderMenuDTO.builder()
                    .id(orderMenu.getId())
                    .menu(orderMenu.getMenuName())
                    .price(orderMenu.getPrice())
                    .count(orderMenu.getCount())
                    .total(orderMenu.getTotal())
                    .build());
        }
        return orderMenuDTOList;
    }
}
