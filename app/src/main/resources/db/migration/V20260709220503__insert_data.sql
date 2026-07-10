INSERT INTO groups (name)
VALUES
    ('Java-101'),
    ('Java-102'),
    ('Frontend'),
    ('QA'),
    ('DevOps');

INSERT INTO courses (name)
VALUES
    ('Java'),
    ('Spring'),
    ('SQL'),
    ('Docker'),
    ('React'),
    ('Git');

INSERT INTO students (first_name, last_name, email, group_id)
VALUES
    ('Ivan', 'Petrenko', 'ivan.petrenko@example.com', 1),
    ('Maria', 'Ivanova', 'maria.ivanova@example.com', 2),
    ('Oleksandr', 'Bondarenko', 'oleksandr.bondarenko@example.com', 3),
    ('Anna', 'Shevchenko', 'anna.shevchenko@example.com', 1),
    ('Dmytro', 'Melnyk', 'dmytro.melnyk@example.com', 4),
    ('Kateryna', 'Kovalenko', 'kateryna.kovalenko@example.com', 2),
    ('Andrii', 'Tkachenko', 'andrii.tkachenko@example.com', 5),
    ('Olena', 'Boyko', 'olena.boyko@example.com', 3),
    ('Maksym', 'Koval', 'maksym.koval@example.com', 1),
    ('Yuliia', 'Savchenko', 'yuliia.savchenko@example.com', 4),
    ('Bohdan', 'Moroz', 'bohdan.moroz@example.com', 2),
    ('Sofiia', 'Lysenko', 'sofiia.lysenko@example.com', 5),
    ('Artem', 'Kravchenko', 'artem.kravchenko@example.com', 3),
    ('Iryna', 'Polishchuk', 'iryna.polishchuk@example.com', 1),
    ('Denys', 'Hrytsenko', 'denys.hrytsenko@example.com', 4),
    ('Nataliia', 'Romaniuk', 'nataliia.romaniuk@example.com', 5),
    ('Roman', 'Marchenko', 'roman.marchenko@example.com', 2),
    ('Viktoriia', 'Zakharchenko', 'viktoriia.zakharchenko@example.com', 3),
    ('Yaroslav', 'Oliinyk', 'yaroslav.oliinyk@example.com', 4),
    ('Alina', 'Chernenko', 'alina.chernenko@example.com', 5);

INSERT INTO student_courses (student_id, course_id)
VALUES
    (1,1),(1,2),(1,3),

    (2,3),(2,5),

    (3,1),(3,2),(3,4),

    (4,1),(4,3),

    (5,4),(5,6),

    (6,3),(6,5),

    (7,4),(7,6),

    (8,5),

    (9,1),(9,2),(9,6),

    (10,3),(10,4),

    (11,2),(11,3),

    (12,4),(12,5),

    (13,1),(13,2),(13,3),

    (14,2),

    (15,4),(15,6),

    (16,5),

    (17,1),(17,3),

    (18,2),(18,5),

    (19,6),

    (20,1),(20,4),(20,5);
