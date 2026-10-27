import { makeAutoObservable } from 'mobx';
import axios from 'axios';

const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:8080';

class AuthStore {
    token = localStorage.getItem('token') || null;
    username = localStorage.getItem('username') || null;
    role = localStorage.getItem('role') || null;

    constructor() {
        makeAutoObservable(this);
        if (this.token) {
            axios.defaults.headers.common['Authorization'] = `Bearer ${this.token}`;
        }
    }

    async register(username, password) {
        await axios.post(`${API_BASE}/auth/register`, { username, password });
    }

    async login(username, password) {
        const { data } = await axios.post(`${API_BASE}/auth/login`, { username, password });
        console.log(data);
        this.token = data.token;
        this.username = data.username;
        this.role = data.role;

        localStorage.setItem('token', data.token);
        localStorage.setItem('username', data.username);
        localStorage.setItem('role', data.role);

        axios.defaults.headers.common['Authorization'] = `Bearer ${data.token}`;
    }

    logout() {
        this.token = null;
        this.username = null;
        this.role = null;
        localStorage.clear();
        delete axios.defaults.headers.common['Authorization'];
    }

    get isAuthenticated() { return !!this.token; }
    get isAdmin() { return this.role === 'ADMIN'; }
}

export const authStore = new AuthStore();