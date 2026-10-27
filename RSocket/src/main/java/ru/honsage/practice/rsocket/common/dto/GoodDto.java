package ru.honsage.practice.rsocket.common.dto;

import ru.honsage.practice.rsocket.server.model.Good;

public record GoodDto(
        int id,
        String name,
        float price
) {

    public static GoodDto from(Good good) {
        return new GoodDto(good.id(), good.name(), good.price());
    }
}