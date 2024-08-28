package com.example.RGT.DTO;

import lombok.Builder;

import java.util.List;

@Builder
public record MyOrderResponseDTO(Long id, List<OrderMenuDTO> orderMenuDTO, Long totalPrice, int status, Long orderTime) {
}
