import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { observer } from 'mobx-react-lite';
import { authStore } from '@stores/authStore';
import styles from './auth-form.module.css';

const AuthForm = observer(({ isRegister }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        try {
            if (isRegister) {
                await authStore.register(username, password);
                navigate('/login');
            } else {
                await authStore.login(username, password);
                navigate('/notes');
            }
        } catch (err) {
            setError('Ошибка. Проверьте логин/пароль.');
        }
    };

    return (
        <div className={styles.formContainer}>
            <h2>{isRegister ? 'Регистрация' : 'Вход'}</h2>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    placeholder="Имя пользователя"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />
                <input
                    type="password"
                    placeholder="Пароль"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                {error && <p className={styles.error}>{error}</p>}
                <button type="submit">
                    {isRegister ? 'Зарегистрироваться' : 'Войти'}
                </button>
            </form>
            <p>
                {isRegister ? (
                    <>Уже есть аккаунт? <a href="/login">Войти</a></>
                ) : (
                    <>Нет аккаунта? <a href="/register">Зарегистрироваться</a></>
                )}
            </p>
        </div>
    );
});

export default AuthForm;