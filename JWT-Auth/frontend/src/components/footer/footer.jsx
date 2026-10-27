import React from 'react';
import styles from './footer.module.css';

const Footer = () => {
  return (
    <footer className={styles.footer}>
      <div className={styles.copyright}>
        &copy; Мусатов И.А., 2026
      </div>
    </footer>
  );
};

export default Footer;