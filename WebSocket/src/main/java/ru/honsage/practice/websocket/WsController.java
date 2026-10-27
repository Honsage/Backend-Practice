package ru.honsage.practice.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WsController {

    @MessageMapping("/echo")
    @SendTo("/topic/echo")
    public Message echo(Message message) {
        return message;
    }
}
