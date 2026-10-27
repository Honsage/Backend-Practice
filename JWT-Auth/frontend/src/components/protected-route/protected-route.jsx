import React from 'react';
import { Navigate } from 'react-router-dom';
import { observer } from 'mobx-react-lite';
import { authStore } from '@stores/authStore';

const ProtectedRoute = observer(({ children, adminOnly = false }) => {
    if (!authStore.isAuthenticated) return <Navigate to="/login" replace />;
    if (adminOnly && !authStore.isAdmin) return <Navigate to="/" replace />;
    return children;
});

export default ProtectedRoute;