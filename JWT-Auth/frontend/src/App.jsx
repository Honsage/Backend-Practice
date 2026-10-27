import React from 'react';
import { Routes, Route } from 'react-router-dom';
import Layout from '@components/layout/layout';
import HomePage from '@pages/home/home';
import RegisterPage from '@pages/register/register';
import LoginPage from '@pages/login/login';
import NotesPage from '@pages/notes/notes';
import AdminPage from '@pages/admin/admin';
import ProtectedRoute from '@components/protected-route/protected-route';

function App() {
  return (
    <Routes>
      <Route element={<Layout />}>
        <Route path="/" element={<HomePage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/login" element={<LoginPage />} />

        <Route path="/notes" element={
          <ProtectedRoute>
            <NotesPage />
          </ProtectedRoute>
        } />

        <Route path="/admin" element={
          <ProtectedRoute adminOnly={true}>
            <AdminPage />
          </ProtectedRoute>
        } />
      </Route>
    </Routes>
  );
}

export default App;