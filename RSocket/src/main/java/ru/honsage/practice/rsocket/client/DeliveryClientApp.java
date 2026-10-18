package ru.honsage.practice.rsocket.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeliveryClientApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DeliveryClientApp.class);
        app.setAdditionalProfiles("client");
        app.run(args);
    }
}
