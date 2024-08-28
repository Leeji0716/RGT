package com.example.RGT.Controller;

import com.example.RGT.DTO.CartRequestDTO;
import com.example.RGT.DTO.CartResponseDTO;
import com.example.RGT.Service.MultiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final MultiService multiService;

    @PostMapping
    public ResponseEntity<?> addToCart(@RequestBody CartRequestDTO cartRequestDTO){
        try {
            CartResponseDTO cartResponseDTO = multiService.addToCart(cartRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(cartResponseDTO);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateCart(@RequestHeader("cartId") Long cartId, @RequestHeader("count") Long count){
        try {
            CartResponseDTO cartResponseDTO = multiService.updateCart(cartId, count);
            return ResponseEntity.status(HttpStatus.OK).body(cartResponseDTO);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> removeMenu(@RequestHeader("cartId") Long cartId){
        try {
            List<CartResponseDTO> cartResponseDTOList = multiService.deleteCart(cartId);
            return ResponseEntity.status(HttpStatus.OK).body(cartResponseDTOList);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getCartMenuList(){
        try {
            List<CartResponseDTO> cartResponseDTOList = multiService.getCartMenuList();
            return ResponseEntity.status(HttpStatus.OK).body(cartResponseDTOList);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }
}
