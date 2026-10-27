package ru.honsage.practice.rsocket.common.dto;

import ru.honsage.practice.rsocket.server.model.Delivery;
import ru.honsage.practice.rsocket.server.model.Status;

import java.time.LocalDateTime;

public record DeliveryDto(
        int id,
        String darkstoreAddress,
        LocalDateTime deliveryDatetime,
        Status status
) {

    public static DeliveryDto from(Delivery delivery) {
        return new DeliveryDto(
                delivery.id(),
                delivery.darkstore().address(),
                delivery.deliveryDatetime(),
                delivery.status()
        );
    }
}
