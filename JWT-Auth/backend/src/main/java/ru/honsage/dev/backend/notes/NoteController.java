package ru.honsage.dev.backend.notes;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public NoteResponse create(
            @RequestBody NoteRequest request,
            Authentication auth
    ) {
        var note = noteService.create(request.getContent(), auth.getName());
        return new NoteResponse(note.getId(), note.getContent());
    }

    @GetMapping
    public List<NoteResponse> getAll(Authentication auth) {
        return noteService.getUserNotes(auth.getName())
                .stream()
                .map(n -> new NoteResponse(n.getId(), n.getContent()))
                .toList();
    }

    @PutMapping("/{id}")
    public NoteResponse update(
            @PathVariable Long id,
            @RequestBody NoteRequest request,
            Authentication auth
    ) {
        var note = noteService.update(id, request.getContent(), auth.getName());
        return new NoteResponse(note.getId(), note.getContent());
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id,
            Authentication auth
    ) {
        noteService.delete(id, auth.getName());
    }
}