SELECT id FROM users;

INSERT INTO users (id, email, activation_code, password, role, name, surname, patronymic, birth_date, sex_id, reward_points, penalty_points)
VALUES ('73b0af32-887c-4903-bc54-af3f96481e9e', 'test@mail.ru', null, '123', 'MAGICIAN', 'Test', 'Testov', 'Testovich', '2002-10-28', 'c99ccd51-5731-42a3-9cfc-31cc4011e035', 0, 0);

INSERT INTO users (id, email, activation_code, password, role, name, surname, patronymic, birth_date, sex_id, reward_points, penalty_points)
VALUES ('85b0af32-887c-4903-bc54-af3f96481e9e', 'test@mail.ru', null, '123', 'MAGICIAN', 'Test', 'Testov', 'Testovich', '2002-10-28', 'c99ccd51-5731-42a3-9cfc-31cc4011e035', 0, 0);

SELECT * FROM users WHERE id='85b0af32-887c-4903-bc54-af3f96481e9e';


INSERT INTO users (id, email, activation_code, password, role, name, surname, patronymic, birth_date, sex_id, reward_points, penalty_points)
VALUES ('85b0af32-887c-4903-bc54-af3f96481e9e', 'test@mail.ru', null, '123', 'MAGICIAN', 'Test', 'Testov', 'Testovich', '2002-10-28', null, 0, 0);

INSERT INTO users (id, email, activation_code, password, role, name, surname, patronymic, birth_date, sex_id, reward_points, penalty_points)
VALUES ('85b0af32-887c-4903-bc54-af3f96481e9e', 'test@mail.ru', null, '123', 'MAGICIAN', 'Test', 'Testov', 'Testovich', '2002-10-28', 'c99ccd51-5731-42a3-9cfc-31cc4011e035', 0, 0);
SELECT * FROM users WHERE id='85b0af32-887c-4903-bc54-af3f96481e9e';