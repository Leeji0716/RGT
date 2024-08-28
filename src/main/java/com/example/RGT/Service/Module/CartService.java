package com.example.RGT.Service.Module;

import com.example.RGT.DTO.CartResponseDTO;
import com.example.RGT.DTO.StoreMenuResponseDTO;
import com.example.RGT.DTO.StoreTableResponseDTO;
import com.example.RGT.Entity.Cart;
import com.example.RGT.Entity.StoreMenu;
import com.example.RGT.Entity.StoreTable;
import com.example.RGT.Repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public CartResponseDTO CartDTO(Cart cart, StoreTableResponseDTO storeTableResponseDTO, StoreMenuResponseDTO storeMenuResponseDTO) {
        Long price = storeMenuResponseDTO.price();
        Long count = cart.getCount();
        Long total = price * count;
        return CartResponseDTO.builder()
                .id(cart.getId())
                .storeTable(storeTableResponseDTO)
                .storeMenu(storeMenuResponseDTO)
                .count(count)
                .total(total)
                .build();
    }

    public Cart getCart(Long cartId) throws Exception{
        return cartRepository.findById(cartId).orElseThrow(() -> new Exception ("cart not found"));
    }

    public List<Cart> getStoreList(StoreTable storeTable) {
        return cartRepository.findByStoreTable(storeTable);
    }

    public Optional<Cart> findByStoreTableAndStoreMenu(StoreTable storeTable, StoreMenu storeMenu) {
        return cartRepository.findByStoreTableAndStoreMenu(storeTable, storeMenu);
    }

    public void remove(List<Cart> cartList) {
        for (Cart cart : cartList){
            cartRepository.delete(cart);
        }
    }

    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }
}
