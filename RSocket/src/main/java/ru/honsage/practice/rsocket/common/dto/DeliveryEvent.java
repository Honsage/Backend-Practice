package ru.honsage.practice.rsocket.common.dto;

public record DeliveryEvent(
        int id,
        int goodId,
        int quantity
) {}
