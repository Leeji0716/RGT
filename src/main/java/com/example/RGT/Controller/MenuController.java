package com.example.RGT.Controller;

import com.example.RGT.DTO.MenuRequestDTO;
import com.example.RGT.DTO.MenuResponseDTO;
import com.example.RGT.Service.MultiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/menu")
public class MenuController {
    private final MultiService multiService;

    @PostMapping
    public ResponseEntity<?> createMenu(@RequestBody MenuRequestDTO menuRequestDTO){
        try {
            MenuResponseDTO menuResponseDTO = multiService.createMenu(menuRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(menuResponseDTO);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }
//
//    @GetMapping
//    public ResponseEntity<?> getMenu(@RequestHeader("menuId") Long menuId){
//        try {
//            MenuResponseDTO menuResponseDTO = multiService.getMenu(menuId);
//            return ResponseEntity.status(HttpStatus.OK).body(menuResponseDTO);
//        }catch (Exception ex){
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
//        }
//    }

    @PutMapping
    public ResponseEntity<?> updateMenu(@RequestHeader("menuId") Long menuId, @RequestBody MenuRequestDTO menuRequestDTO){
        try {
            MenuResponseDTO menuResponseDTO = multiService.updateMenu(menuId, menuRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(menuResponseDTO);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }
}
