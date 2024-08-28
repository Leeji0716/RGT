package com.example.RGT.DTO;

import lombok.Builder;

@Builder
public record CartResponseDTO(Long id, StoreTableResponseDTO storeTable, StoreMenuResponseDTO storeMenu, Long count, Long total) {
}
