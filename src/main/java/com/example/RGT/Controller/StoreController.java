package com.example.RGT.Controller;

import com.example.RGT.DTO.*;
import com.example.RGT.Service.MultiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/store")
public class StoreController {
    private final MultiService multiService;

    @GetMapping("/list")
    public ResponseEntity<?> getStoreList(){
        try {
            List<StoreResponseDTO> storeResponseDTOList = multiService.getStoreList();
            return ResponseEntity.status(HttpStatus.OK).body(storeResponseDTOList);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createStore(@RequestBody StoreRequestDTO storeRequestDTO){
        try {
            StoreResponseDTO storeResponseDTO = multiService.createStore(storeRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(storeResponseDTO);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }

    @GetMapping("/menuList")
    public ResponseEntity<?> getMenuList(@RequestHeader("storeId") Long storeId){
        try {
            List<StoreMenuResponseDTO> storeMenuResponseDTOList = multiService.getStoreMenuList(storeId);
            return ResponseEntity.status(HttpStatus.OK).body(storeMenuResponseDTOList);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }

    @GetMapping("/menu")
    public ResponseEntity<?> getMenuDetail(@RequestHeader("storeMenuId") Long storeMenuId){
        try {
            MenuResponseDTO menuResponseDTO = multiService.getStoreMenuDetail(storeMenuId);
            return ResponseEntity.status(HttpStatus.OK).body(menuResponseDTO);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> registrationMenu(@RequestHeader("storeId") Long storeId,
                                                 @RequestHeader("menuId") Long menuId){
        try {
            List<StoreMenuResponseDTO> storeMenuResponseDTOList = multiService.registration(storeId, menuId);
            return ResponseEntity.status(HttpStatus.OK).body(storeMenuResponseDTOList);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }

    @PutMapping("/select")
    public ResponseEntity<?> selectStoreAndTable(@RequestHeader("storeTableId") Long storeTableId){
        try {
            StoreTableResponseDTO storeTableResponseDTO = multiService.select(storeTableId);
            return ResponseEntity.status(HttpStatus.OK).body(storeTableResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/tableList")
    public ResponseEntity<?> getTableList(@RequestHeader("storeId") Long storeId){
        try {
            List<StoreTableResponseDTO> storeTableResponseDTOList = multiService.getStoreTableList(storeId);
            return ResponseEntity.status(HttpStatus.OK).body(storeTableResponseDTOList);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }
}
