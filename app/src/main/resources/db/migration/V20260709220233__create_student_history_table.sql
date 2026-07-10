CREATE TABLE student_history (
                                 id SERIAL PRIMARY KEY,
                                 student_id INTEGER NOT NULL REFERENCES students(id),
                                 old_group_id INTEGER NOT NULL REFERENCES groups(id),
                                 new_group_id INTEGER NOT NULL REFERENCES groups(id),
                                 changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
