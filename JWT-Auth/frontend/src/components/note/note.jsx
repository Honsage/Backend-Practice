import React from "react";
import { Link } from "react-router-dom";
import styles from ".note.module.css";

const Note = ({ id, content }) => {
    return (
        <div className={styles.noteBlock}>
            <p>{author}</p>
            <p>{description}</p>            
        </div>
    );
};

export default Note;