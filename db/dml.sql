/* Tables cleanup */
DELETE FROM account_text_answer;
DELETE FROM account_quiz_answer;
DELETE FROM answer;
DELETE FROM question;
DELETE FROM quiz;
DELETE FROM lesson_attendance;
DELETE FROM lesson;
DELETE FROM account_class;
DELETE FROM system_class;
DELETE FROM logged_device;
DELETE FROM notice;
DELETE FROM credentials;
DELETE FROM account;
DELETE FROM account_role;

/* Auto incremental keys reset */
ALTER SEQUENCE account_role_role_id_seq RESTART WITH 1;
ALTER SEQUENCE account_account_id_seq RESTART WITH 1;
ALTER SEQUENCE credentials_credentials_id_seq RESTART WITH 1;
ALTER SEQUENCE notice_notice_id_seq RESTART WITH 1;
ALTER SEQUENCE logged_device_device_id_seq RESTART WITH 1;
ALTER SEQUENCE lesson_lesson_id_seq RESTART WITH 1;
ALTER SEQUENCE system_class_class_id_seq RESTART WITH 1;
ALTER SEQUENCE quiz_quiz_id_seq RESTART WITH 1;
ALTER SEQUENCE question_question_id_seq RESTART WITH 1;
ALTER SEQUENCE answer_answer_id_seq RESTART WITH 1;

/* Sample data generation */
INSERT INTO account_role (role_name) VALUES
    ('ADMIN'),
    ('TEACHER'),
    ('STUDENT');

INSERT INTO account (first_name, last_name, icon, role_id) VALUES
    ('John', 'Smith', '/icons/john.jpg', 1),
    ('Mary', 'Johnson', '/icons/mary.jpg', 2),
    ('David', 'Williams', '/icons/david.jpg', 2),
    ('Emma', 'Brown', null, 3),
    ('James', 'Davis', '/icons/james.jpg', 3);

/* NOTE: password is 'password123' hashed using Argon2id */
INSERT INTO credentials (email, password, updated_at, account_id) VALUES
    ('john.smith@edu.com', '$argon2id$v=19$m=65536,t=4,p=4$JvdlFV4eSxQ5dGP0uxERIQ$7rqxJO+gCN3B1GWSHOYo1cL0Fg7fCnMlZqPFPPBQXBA', 1699570800, 1),
    ('mary.j@edu.com', '$argon2id$v=19$m=65536,t=4,p=4$JvdlFV4eSxQ5dGP0uxERIQ$7rqxJO+gCN3B1GWSHOYo1cL0Fg7fCnMlZqPFPPBQXBA', 1699570800, 2),
    ('david.w@edu.com', '$argon2id$v=19$m=65536,t=4,p=4$JvdlFV4eSxQ5dGP0uxERIQ$7rqxJO+gCN3B1GWSHOYo1cL0Fg7fCnMlZqPFPPBQXBA', 1699570800, 3),
    ('emma.b@edu.com', '$argon2id$v=19$m=65536,t=4,p=4$JvdlFV4eSxQ5dGP0uxERIQ$7rqxJO+gCN3B1GWSHOYo1cL0Fg7fCnMlZqPFPPBQXBA', 1699570800, 4),
    ('james.d@edu.com', '$argon2id$v=19$m=65536,t=4,p=4$JvdlFV4eSxQ5dGP0uxERIQ$7rqxJO+gCN3B1GWSHOYo1cL0Fg7fCnMlZqPFPPBQXBA', 1699570800, 5);

INSERT INTO system_class (course_name, class_address, class_year, teacher_id) VALUES
    ('Mathematics 101', 'Building A', 2023, 2),
    ('Physics 101', 'Building B', 2023, 3);

INSERT INTO account_class (account_id, class_id) VALUES
    (4, 1),
    (5, 1),
    (4, 2),
    (5, 2);

INSERT INTO lesson (lesson_date, starts_at, ends_at, room_no, account_id, class_id) VALUES
    ('2023-11-15', '09:00:00', '10:30:00', 101, 2, 1),
    ('2023-11-16', '11:00:00', '12:30:00', 102, 3, 2);

INSERT INTO lesson_attendance (account_id, lesson_id, attended) VALUES
    (4, 1, true),
    (5, 1, false),
    (4, 2, true),
    (5, 2, true);

INSERT INTO quiz (title, class_id) VALUES
    ('Midterm Math Quiz', 1),
    ('Physics Basics', 2);

INSERT INTO question (question_label, quiz_id) VALUES
    ('What is 2 + 2?', 1),
    ('Solve: x² + 5x + 6 = 0', 1),
    ('What is Newton''s First Law?', 2),
    ('Convert 1 kilometer to meters', 2);

INSERT INTO answer (answer_label, is_correct, question_id) VALUES
    ('4', true, 1),
    ('3', false, 1),
    ('x = -2 or x = -3', true, 2),
    ('x = 2 or x = 3', false, 2),
    ('An object remains at rest or in motion unless acted upon by a force', true, 3),
    ('Force equals mass times acceleration', false, 3),
    ('1000 meters', true, 4),
    ('100 meters', false, 4);

INSERT INTO account_quiz_answer (account_id, answer_id) VALUES
    (4, 1),
    (5, 2),
    (4, 3),
    (5, 5);

INSERT INTO notice (notice_message, account_id) VALUES
    ('Welcome to the new semester!', null),
    ('Your Math homework is due tomorrow', 4),
    ('Class canceled for tomorrow', 5);