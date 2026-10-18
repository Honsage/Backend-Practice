package ru.honsage.practice.rsocket.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.honsage.practice.rsocket.common.dto.DeliveryAck;
import ru.honsage.practice.rsocket.common.dto.DeliveryEvent;
import ru.honsage.practice.rsocket.common.dto.GoodDto;

import java.time.Duration;

@Component
@Profile("client")
public class DeliveryClientRunner implements CommandLineRunner {

    private final RSocketRequester requester;

    public DeliveryClientRunner(RSocketRequester.Builder builder) {
        this.requester = builder
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .tcp("localhost", 7000);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("RSocket Client is started");

        // Request-Response
        System.out.println("\nRequest-Response");
        requester.route("goods.byId")
                .data(1)
                .retrieveMono(GoodDto.class)
                .doOnNext(g -> System.out.println("[RESPONSE] " + g))
                .block();

        // Request-Response с ошибкой
        System.out.println("\nRequest-Response (not found)");
        requester.route("goods.byId")
                .data(999)
                .retrieveMono(GoodDto.class)
                .doOnError(e -> System.err.println("[ERROR HANDLED] " + e.getMessage()))
                .onErrorResume(e -> Mono.empty())
                .block();

        // Request-Stream
        System.out.println("\nRequest-Stream");
        requester.route("goods.stream")
                .retrieveFlux(GoodDto.class)
                .doOnNext(g -> System.out.println("[STREAM ITEM] " + g))
                .blockLast();

        // Fire-and-Forget
        System.out.println("\nFire-and-Forget");
        DeliveryEvent fnfEvent = new DeliveryEvent(1, 2, 5);
        requester.route("delivery.event")
                .data(fnfEvent)
                .send()
                .doOnSuccess(v -> System.out.println("[FNF] Sent without waiting response"))
                .doOnError(e -> System.err.println("[FNF ERROR] " + e.getMessage()))
                .subscribe();

        Thread.sleep(500);

        // Channel
        System.out.println("\nChannel");
        Flux<DeliveryEvent> requests = Flux.just(
                new DeliveryEvent(1, 1, 10),
                new DeliveryEvent(2, 2, 20),
                new DeliveryEvent(3, 3, 30)
        ).delayElements(Duration.ofMillis(500));

        requester.route("delivery.channel")
                .data(requests)
                .retrieveFlux(DeliveryAck.class)
                .doOnNext(ack -> System.out.println("[CHANNEL ACK] " + ack))
                .blockLast();

        System.out.println("\nRSocket Client is finished");
    }
}

