package ru.honsage.practice.rsocket.common.dto;

import ru.honsage.practice.rsocket.server.model.Status;

import java.time.LocalDateTime;

public record DeliveryAck(
        int eventId,
        Status status,
        LocalDateTime processedAt
) {}
