package com.example.RGT.DTO;

import lombok.Builder;

import java.util.List;

@Builder
public record StoreResponseDTO(Long id, String name, List<StoreTableResponseDTO> storeTablesDTO) {
}
