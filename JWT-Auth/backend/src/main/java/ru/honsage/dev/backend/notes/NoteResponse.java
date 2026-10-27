package ru.honsage.dev.backend.notes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoteResponse {
    private Long id;
    private String content;
}