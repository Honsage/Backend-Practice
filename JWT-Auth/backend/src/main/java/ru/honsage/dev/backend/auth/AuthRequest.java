package ru.honsage.dev.backend.auth;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
