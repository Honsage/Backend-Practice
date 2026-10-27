import React from 'react';
import styles from './home.module.css';

const HomePage = () => {
  return (
    <div className={styles.home}>
      <h1 className={styles.title}>Добро пожаловать в Notes App</h1>
      <p className={styles.text}>
        Простое приложение для создания и хранения заметок.
      </p>
    </div>
  );
};

export default HomePage;