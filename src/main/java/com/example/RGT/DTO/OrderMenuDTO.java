package com.example.RGT.DTO;

import lombok.Builder;

@Builder
public record OrderMenuDTO(Long id, String menu, Long price, Long count, Long total) {
}
