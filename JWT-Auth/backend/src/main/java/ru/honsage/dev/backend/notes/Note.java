package ru.honsage.dev.backend.notes;

import jakarta.persistence.*;
import lombok.*;
import ru.honsage.dev.backend.user.User;

@Entity
@Table(name = "notes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    private User user;
}