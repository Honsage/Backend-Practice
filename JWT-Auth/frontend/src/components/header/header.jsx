import React from 'react';
import { NavLink } from 'react-router-dom';
import { observer } from 'mobx-react-lite';
import { authStore } from '@stores/authStore';
import styles from './header.module.css';

const Header = observer(() => {
    return (
        <header className={styles.header}>
            <div className={styles.container}>
                <NavLink to="/" className={styles.logo}>Notes App</NavLink>

                <nav className={styles.nav}>
                    <NavLink to="/" className={({ isActive }) => `${styles.link} ${isActive ? styles.active : ''}`}>Главная</NavLink>
                    <NavLink to="/notes" className={({ isActive }) => `${styles.link} ${isActive ? styles.active : ''}`}>Мои заметки</NavLink>

                    {authStore.isAdmin && (
                        <NavLink to="/admin" className={({ isActive }) => `${styles.link} ${isActive ? styles.active : ''}`}>Админ-панель</NavLink>
                    )}

                    {!authStore.isAuthenticated ? (
                        <>
                            <NavLink to="/login" className={({ isActive }) => `${styles.link} ${isActive ? styles.active : ''}`}>Вход</NavLink>
                            <NavLink to="/register" className={({ isActive }) => `${styles.link} ${isActive ? styles.active : ''}`}>Регистрация</NavLink>
                        </>
                    ) : (
                        <>
                            <span className={styles.user}>{authStore.username}</span>
                            <button onClick={() => { authStore.logout(); window.location.href = '/'; }} className={styles.logoutBtn}>
                                Выйти
                            </button>
                        </>
                    )}
                </nav>
            </div>
        </header>
    );
});

export default Header;