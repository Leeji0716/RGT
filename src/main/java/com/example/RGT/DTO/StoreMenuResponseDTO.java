package com.example.RGT.DTO;

import lombok.Builder;

@Builder
public record StoreMenuResponseDTO (Long id, String name, Long price){
}
