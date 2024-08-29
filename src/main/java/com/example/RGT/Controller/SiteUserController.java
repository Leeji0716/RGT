package com.example.RGT.Controller;

import com.example.RGT.Service.MultiService;
import com.example.RGT.DTO.UserRequestDTO;
import com.example.RGT.DTO.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class SiteUserController {
    private final MultiService multiService;

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserRequestDTO userRequestDTO){
        try {
            UserResponseDTO userResponseDTO = multiService.createUser(userRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDTO userRequestDTO){
        try {
            UserResponseDTO userResponseDTO = multiService.login(userRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/logout")
    public ResponseEntity<?> logout(){
        try {
            UserResponseDTO userResponseDTO = multiService.logout();
            return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
