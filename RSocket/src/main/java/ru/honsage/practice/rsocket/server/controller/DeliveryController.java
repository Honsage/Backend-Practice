package ru.honsage.practice.rsocket.server.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.honsage.practice.rsocket.common.dto.DeliveryAck;
import ru.honsage.practice.rsocket.common.dto.DeliveryEvent;
import ru.honsage.practice.rsocket.common.dto.GoodDto;
import ru.honsage.practice.rsocket.server.model.Good;
import ru.honsage.practice.rsocket.server.model.Status;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
public class DeliveryController {

    private final Map<Integer, Good> goods = Map.of(
            1, new Good(1, "Молоко", 89.90f),
            2, new Good(2, "Хлеб", 45.50f),
            3, new Good(3, "Сыр", 320.00f),
            4, new Good(4, "Яйца", 129.90f),
            5, new Good(5, "Масло", 210.00f)
    );

    // Request-Response
    @MessageMapping("goods.byId")
    public Mono<GoodDto> getGoodById(int id) {
        return Mono.justOrEmpty(goods.get(id))
                .map(GoodDto::from)
                .switchIfEmpty(Mono.error(
                        new IllegalArgumentException("Good not found: " + id)));
    }

    // Request-Stream
    @MessageMapping("goods.stream")
    public Flux<GoodDto> streamGoods() {
        return Flux.fromIterable(goods.values())
                .map(GoodDto::from)
                .delayElements(Duration.ofSeconds(1));
    }

    // Fire-and-Forget
    @MessageMapping("delivery.event")
    public Mono<Void> fireAndForget(DeliveryEvent event) {
        System.out.printf(
                "[FNF] Received event: id=%d, goodId=%d, qty=%d%n",
                event.id(),
                event.goodId(),
                event.quantity()
        );
        return Mono.empty();
    }

    // Channel
    @MessageMapping("delivery.channel")
    public Flux<DeliveryAck> channel(Flux<DeliveryEvent> events) {
        return events
                .doOnNext(e -> System.out.printf(
                        "[CHANNEL] Processing: id=%d, goodId=%d, qty=%d%n",
                        e.id(),
                        e.goodId(),
                        e.quantity()
                ))
                .map(e ->
                        new DeliveryAck(
                                e.id(), Status.SUBMITTED, LocalDateTime.now())
                );
    }
}

