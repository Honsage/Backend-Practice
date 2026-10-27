package ru.honsage.dev.backend.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.honsage.dev.backend.user.User;
import ru.honsage.dev.backend.user.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
