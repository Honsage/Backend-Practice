import { makeAutoObservable } from 'mobx';
import axios from 'axios';

const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:8080';

class NotesStore {
    notes = [];

    constructor() {
        makeAutoObservable(this);
    }

    async fetchNotes() {
        const { data } = await axios.get(`${API_BASE}/notes`);
        this.notes = data;
    }

    async createNote(content) {
        const { data } = await axios.post(`${API_BASE}/notes`, { content });
        this.notes.unshift(data);
    }

    async updateNote(id, content) {
        await axios.put(`${API_BASE}/notes/${id}`, { content });
        this.fetchNotes();
    }

    async deleteNote(id) {
        await axios.delete(`${API_BASE}/notes/${id}`);
        this.notes = this.notes.filter(n => n.id !== id);
    }
}

export const notesStore = new NotesStore();