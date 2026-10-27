import React from 'react';
import { Outlet } from 'react-router-dom';
import Header from '@components/header/header';
import Footer from '@components/footer/footer';
import styles from './layout.module.css';

const Layout = () => {
  return (
    <div className={styles.layout}>
      <Header />
      <main className={styles.main}>
        <Outlet />
      </main>
      <Footer />
    </div>
  );
};

export default Layout;