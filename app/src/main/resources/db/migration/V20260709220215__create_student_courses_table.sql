CREATE TABLE student_courses (
                                 student_id INTEGER NOT NULL REFERENCES students(id),
                                 course_id INTEGER NOT NULL REFERENCES courses(id),
                                 PRIMARY KEY (student_id, course_id)
);
