package ru.honsage.dev.backend.notes;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.honsage.dev.backend.user.User;
import ru.honsage.dev.backend.user.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Transactional
    public Note create(String content, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Note note = Note.builder()
                .content(content)
                .user(user)
                .build();
        return noteRepository.save(note);
    }

    @Transactional
    public Note update(Long id, String content, String username) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (!note.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }

        note.setContent(content);
        return noteRepository.save(note);
    }

    @Transactional
    public void delete(Long id, String username) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (!note.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }

        noteRepository.delete(note);
    }

    public List<Note> getUserNotes(String username) {
        return noteRepository.findAllByUserUsername(username);
    }
}