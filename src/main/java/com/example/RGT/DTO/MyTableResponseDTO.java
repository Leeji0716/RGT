package com.example.RGT.DTO;

import lombok.Builder;

@Builder
public record MyTableResponseDTO(Long id, String tableNumber) {
}
