package com.example.RGT.Controller;

import com.example.RGT.DTO.MyOrderResponseDTO;
import com.example.RGT.Service.MultiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class MyOrderController {
    private final MultiService multiService;

    @PostMapping
    public ResponseEntity<?> order(){
        try {
            MyOrderResponseDTO myOrderResponseDTO = multiService.order();
            return ResponseEntity.status(HttpStatus.OK).body(myOrderResponseDTO);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }

    @PutMapping("/complete")
    public ResponseEntity<?> complete(@RequestHeader("orderId") Long orderId){
        try {
            MyOrderResponseDTO myOrderResponseDTO = multiService.complete(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(myOrderResponseDTO);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }

    @PutMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestHeader("orderId") Long orderId){
        try {
            MyOrderResponseDTO myOrderResponseDTO = multiService.cancel(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(myOrderResponseDTO);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> checkOrder(@RequestHeader("orderId") Long orderId){
        try {
            MyOrderResponseDTO myOrderResponseDTO = multiService.checkOrder(orderId);
            return ResponseEntity.status(HttpStatus.OK).body(myOrderResponseDTO);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }

}
