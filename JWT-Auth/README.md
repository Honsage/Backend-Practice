# Notes App

Full-stack application for managing notes with JWT authentication and role-based access control (USER/ADMIN).

Aimed to practice authentication, CRUD and RBAC.

## Tech Stack

**Backend:** Spring Boot 3, Spring Security, JWT, JPA (Hibernate), PostgreSQL, Maven

**Frontend:** React 18, Vite, React Router DOM, MobX (state management), Axios

**DevOps:** Docker, Docker Compose

## Project Structure

```
.
├── backend/          # Spring Boot app (port 8080)
├── frontend/         # React app (port 3000)
└── docker-compose.yaml
```

---

## Backend

**Directory:** `/backend`

Java application with built-in JWT authentication. Users have two roles: `USER` (regular user) and `ADMIN` (administrator).

### API Endpoints

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| POST | `/auth/register` | Register new user | public |
| POST | `/auth/login` | Authenticate, returns JWT | public |
| GET | `/notes` | Get all notes of current user | USER, ADMIN |
| POST | `/notes` | Create a note | USER, ADMIN |
| PUT | `/notes/{id}` | Update a note | USER, ADMIN |
| DELETE | `/notes/{id}` | Delete a note | USER, ADMIN |
| GET | `/admin/users` | Get all users | ADMIN |

### Configuration

**File:** `backend/src/main/resources/application.yaml`

| Property | Default | Description |
|----------|---------|-------------|
| `server.port` | 8080 | Server port |
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/postgres` | Database URL |
| `spring.datasource.username` | postgres | Database username |
| `spring.datasource.password` | root | Database password |
| `jwt.secret` | (required via env) | Secret key for JWT signing |
| `jwt.expiration` | 3600000 | Token lifetime (ms) |

### Packages

| Package | Purpose |
|---------|---------|
| `auth` | Controller and service for register/login |
| `security` | JWT filter, token service, Spring Security config |
| `notes` | CRUD operations with notes |
| `user` | User repository, custom UserDetailsService |
| `admin` | Admin controller (view all users) |
| `config` | CORS and SecurityFilterChain settings |

---

## Frontend

**Directory:** `/frontend`

React SPA with routing and MobX stores for state management.

### Routes

| Path | Component | Description | Access |
|------|-----------|-------------|--------|
| `/` | `HomePage` | Home page | public |
| `/register` | `RegisterPage` | Registration form | public |
| `/login` | `LoginPage` | Login form | public |
| `/notes` | `NotesPage` | User's notes list | authenticated |
| `/admin` | `AdminPage` | Admin panel | ADMIN role required |

### Components

| Component | Purpose |
|-----------|---------|
| `Layout` | Wrapper with header/footer, renders child pages via Outlet |
| `Header` | Navigation (links to /notes, /admin, Logout button) |
| `Footer` | App footer |
| `AuthForm` | Reusable login/registration form |
| `Note` | Note card with Edit/Delete buttons |
| `ProtectedRoute` | Wrapper component for route protection |

### Pages

| Page | Purpose |
|------|---------|
| `HomePage` | Welcome page with project description |
| `LoginPage` | Login page (uses AuthForm) |
| `RegisterPage` | Registration page |
| `NotesPage` | Displays notes list, form to create new notes |
| `AdminPage` | Table with all users |

### Stores

**authStore** — authentication management

| Field / Method | Purpose |
|----------------|---------|
| `token` | JWT token (stored in localStorage) |
| `username` | Current user's username |
| `role` | User role (USER/ADMIN) |
| `isAuthenticated` | getter: `!!token` |
| `isAdmin` | getter: `role === 'ADMIN'` |
| `register()` | POST `/auth/register` |
| `login()` | POST `/auth/login`, saves token to localStorage and Axios headers |
| `logout()` | Clears localStorage and Axios headers |

**notesStore** — notes management

| Method | Purpose |
|--------|---------|
| `fetchNotes()` | GET `/notes`, populates `notes` |
| `createNote(content)` | POST `/notes` |
| `updateNote(id, content)` | PUT `/notes/{id}` |
| `deleteNote(id)` | DELETE `/notes/{id}` |

---

## Running the Project

### Local Run (without Docker Compose)

**1. Start PostgreSQL:**
```bash
docker run -d --name postgres -e POSTGRES_PASSWORD=root -p 5432:5432 postgres
```

**2. Start backend:**
```bash
cd backend
export JWT_SECRET=your_secret_key_here
./mvnw spring-boot:run
```

**3. Start frontend:**
```bash
cd frontend
npm install
npm run dev
```

### Run with Docker Compose

```bash
export JWT_SECRET=your_secret_key_here
docker-compose up --build
```

---

## Environment Variables

| Variable | Where | Default |
|----------|-------|---------|
| `JWT_SECRET` | backend (required) | – |
| `VITE_API_BASE` | frontend (optional) | `http://localhost:8080` |