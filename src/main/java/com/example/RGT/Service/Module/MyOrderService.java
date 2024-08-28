package com.example.RGT.Service.Module;

import com.example.RGT.DTO.OrderMenuDTO;
import com.example.RGT.DTO.MyOrderResponseDTO;
import com.example.RGT.ETC.Status;
import com.example.RGT.Entity.MyOrder;
import com.example.RGT.Repository.MyOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyOrderService {
    private final MyOrderRepository myOrderRepository;

    private Long dateTimeTransfer(LocalDateTime dateTime) {
        return dateTime == null ? 0 : dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public MyOrder create() {
        MyOrder myOrder = MyOrder.builder()
                .status(Status.CONFIRM)
                .build();
        return myOrderRepository.save(myOrder);
    }

    public MyOrderResponseDTO OrderDTO(MyOrder myOrder, List<OrderMenuDTO> orderMenuDTOList) {
        Long orderTime = dateTimeTransfer(myOrder.getOrderTime());
        return MyOrderResponseDTO.builder()
                .id(myOrder.getId())
                .totalPrice(myOrder.getTotalPrice())
                .status(myOrder.getStatus().ordinal())
                .orderMenuDTO(orderMenuDTOList)
                .orderTime(orderTime)
                .build();
    }

    public MyOrder save(MyOrder myOrder) {
        return myOrderRepository.save(myOrder);
    }

    public MyOrder getMyOrder(Long orderId) throws Exception{
        return myOrderRepository.findById(orderId).orElseThrow(() -> new Exception ("order not found"));
    }
}
