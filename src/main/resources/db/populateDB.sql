DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_user_seq RESTART WITH 100000;
ALTER SEQUENCE global_meal_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2021-01-22 13:21', 'dinner', 1550),
       (100000, '2021-01-24 19:00', 'dinner1', 1000),
       (100001, '2021-01-21 19:40', 'lunch', 1770);
