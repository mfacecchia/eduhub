DROP TABLE IF EXISTS account_text_answer;
DROP TABLE IF EXISTS account_quiz_answer;
DROP TABLE IF EXISTS answer;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS quiz;
DROP TABLE IF EXISTS lesson_attendance;
DROP TABLE IF EXISTS account_class;
DROP TABLE IF EXISTS lesson;
DROP TABLE IF EXISTS system_class;
DROP TABLE IF EXISTS logged_device;
DROP TABLE IF EXISTS notice;
DROP TABLE IF EXISTS credentials;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS account_role;

/* Tables creation */

CREATE TABLE account_role (
    role_id BIGSERIAL PRIMARY KEY,
    role_name VARCHAR(40) NOT NULL UNIQUE
);

CREATE TABLE account (
    account_id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    icon VARCHAR(100),
    role_id BIGINT NOT NULL
);

CREATE TABLE credentials (
    credentials_id BIGSERIAL PRIMARY KEY,
    email VARCHAR(40) NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    updated_at BIGINT NOT NULL,
    account_id BIGINT NOT NULL UNIQUE
);

CREATE TABLE notice (
    notice_id BIGSERIAL PRIMARY KEY,
    notice_message VARCHAR(200) NOT NULL,
    account_id BIGINT NOT NULL
);

CREATE TABLE logged_device (
    device_id BIGSERIAL PRIMARY KEY,
    brand VARCHAR(30) NOT NULL,
    first_login TIMESTAMPTZ(0) NOT NULL,
    browser_name VARCHAR(30) NOT NULL,
    token_id UUID NOT NULL,
    account_id BIGINT NOT NULL
);

CREATE TABLE lesson (
    lesson_id BIGSERIAL PRIMARY KEY,
    lesson_date DATE NOT NULL,
    starts_at TIME NOT NULL,
    ends_at TIME NOT NULL,
    room_no INT NOT NULL,
    account_id BIGINT,
    class_id BIGINT NOT NULL
);

CREATE TABLE system_class (
    class_id BIGSERIAL PRIMARY KEY,
    -- E.g. "Java, React"
    course_name VARCHAR(30) NOT NULL,
    -- E.g. "Software Development"
    class_address VARCHAR(30) NOT NULL,
    -- E.g. 2023 --> 2023/2024
    class_year INT NOT NULL,
    teacher_id BIGINT
);

CREATE TABLE account_class (
    account_id BIGINT NOT NULL,
    class_id BIGINT NOT NULL
);

CREATE TABLE lesson_attendance (
    account_id BIGINT NOT NULL,
    lesson_id BIGINT NOT NULL,
    attended BOOLEAN NOT NULL DEFAULT false
);

CREATE TABLE quiz (
    quiz_id BIGSERIAL PRIMARY KEY,
    title VARCHAR(40) NOT NULL,
    class_id BIGINT NOT NULL
);

CREATE TABLE question (
    question_id BIGSERIAL PRIMARY KEY,
    question_label VARCHAR(500) NOT NULL,
    quiz_id BIGINT NOT NULL
);

CREATE TABLE answer (
    answer_id BIGSERIAL PRIMARY KEY,
    answer_label VARCHAR(100) NOT NULL,
    is_correct BOOLEAN NOT NULL DEFAULT false,
    question_id BIGINT NOT NULL
);

CREATE TABLE account_quiz_answer (
    account_id BIGINT NOT NULL,
    answer_id BIGINT NOT NULL
);

CREATE TABLE account_text_answer (
    account_id BIGINT NOT NULL,
    quiz_id BIGINT NOT NULL,
    answer_text VARCHAR(500) NOT NULL
);

/* Foreign keys definition */

ALTER TABLE account
ADD CONSTRAINT fk_account_account_role
FOREIGN KEY (role_id) REFERENCES account_role(role_id);

ALTER TABLE credentials
ADD CONSTRAINT fk_credentials_account
FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE;

ALTER TABLE notice
ADD CONSTRAINT fk_notice_account
FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE;

ALTER TABLE logged_device
ADD CONSTRAINT fk_logged_device_account
FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE;

ALTER TABLE lesson
ADD CONSTRAINT fk_lesson_account
FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE SET NULL,
ADD CONSTRAINT fk_lesson_class
FOREIGN KEY (class_id) REFERENCES system_class(class_id) ON DELETE CASCADE;

ALTER TABLE system_class
ADD CONSTRAINT fk_system_class_teacher
FOREIGN KEY (teacher_id) REFERENCES account(account_id) ON DELETE SET NULL;

ALTER TABLE account_class
ADD CONSTRAINT fk_account_class_account
FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE,
ADD CONSTRAINT fk_account_class_class
FOREIGN KEY (class_id) REFERENCES system_class(class_id) ON DELETE CASCADE;

ALTER TABLE lesson_attendance
ADD CONSTRAINT fk_lesson_attendance_account
FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE,
ADD CONSTRAINT fk_lesson_attendance_lesson
FOREIGN KEY (lesson_id) REFERENCES lesson(lesson_id) ON DELETE CASCADE;

ALTER TABLE quiz
ADD CONSTRAINT fk_quiz_class
FOREIGN KEY (class_id) REFERENCES system_class(class_id) ON DELETE CASCADE;

ALTER TABLE question
ADD CONSTRAINT fk_question_quiz
FOREIGN KEY (quiz_id) REFERENCES quiz(quiz_id) ON DELETE CASCADE;

ALTER TABLE answer
ADD CONSTRAINT fk_answer_question
FOREIGN KEY (question_id) REFERENCES question(question_id) ON DELETE CASCADE;

ALTER TABLE account_quiz_answer
ADD CONSTRAINT fk_account_quiz_answer_account
FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE,
ADD CONSTRAINT fk_account_quiz_answer_answer
FOREIGN KEY (answer_id) REFERENCES answer(answer_id) ON DELETE CASCADE;

ALTER TABLE account_text_answer
ADD CONSTRAINT fk_account_text_answer_account
FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE,
ADD CONSTRAINT fk_account_text_answer_quiz
FOREIGN KEY (quiz_id) REFERENCES quiz(quiz_id) ON DELETE CASCADE;