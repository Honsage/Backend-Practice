import React, { useEffect, useState } from 'react';
import { observer } from 'mobx-react-lite';
import { notesStore } from '@stores/notesStore';
import styles from './notes.module.css';

const NotesPage = observer(() => {
  const [content, setContent] = useState('');

  useEffect(() => {
    notesStore.fetchNotes();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!content.trim()) return;
    await notesStore.createNote(content);
    setContent('');
  };

  return (
    <div className={styles.container}>
      <h1>Мои заметки</h1>

      <form onSubmit={handleSubmit} className={styles.form}>
        <textarea
          value={content}
          onChange={(e) => setContent(e.target.value)}
          placeholder="Напишите новую заметку..."
          rows="4"
        />
        <button type="submit">Добавить заметку</button>
      </form>

      <div className={styles.notesList}>
        {notesStore.notes.length === 0 ? (
          <p className={styles.empty}>У Вас еще нет заметок. Создайте их!</p>
        ) : (
          notesStore.notes.map((note) => (
            <div key={note.id} className={styles.noteCard}>
              <p>{note.content}</p>
              <div className={styles.actions}>
                <button
                  onClick={() => {
                    const newText = prompt('Редактировать заметку:', note.content);
                    if (newText !== null) notesStore.updateNote(note.id, newText);
                  }}
                >
                  E
                </button>
                <button onClick={() => notesStore.deleteNote(note.id)}>X</button>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
});

export default NotesPage;