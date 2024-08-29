package com.example.RGT.Controller;

import com.example.RGT.DTO.MyTableResponseDTO;
import com.example.RGT.Service.MultiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/myTable")
public class MyTableController {
    private final MultiService multiService;
    @PostMapping
    public ResponseEntity<?> addMyTable(){
        try {
            List<MyTableResponseDTO> myTableDTOList = multiService.createMyTable();
            return ResponseEntity.status(HttpStatus.OK).body(myTableDTOList);
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        }
    }
}
