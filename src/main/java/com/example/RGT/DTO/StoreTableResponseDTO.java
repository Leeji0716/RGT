package com.example.RGT.DTO;

import lombok.Builder;

@Builder
public record StoreTableResponseDTO(Long id, String store, String myTable) {
}
