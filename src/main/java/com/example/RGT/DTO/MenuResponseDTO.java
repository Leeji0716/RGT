package com.example.RGT.DTO;

import lombok.Builder;

@Builder
public record MenuResponseDTO(Long id, String name, Long price, String content) {
}
