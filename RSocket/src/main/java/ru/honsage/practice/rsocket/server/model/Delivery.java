package ru.honsage.practice.rsocket.server.model;

import java.time.LocalDateTime;

public record Delivery(
        int id,
        Darkstore darkstore,
        LocalDateTime deliveryDatetime,
        Status status
) {}
