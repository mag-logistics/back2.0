insert into sex (id, name) values ('c99ccd51-5731-42a3-9cfc-31cc4011e035','Мужской');
insert into sex (id, name) values ('848f4054-a9c1-4525-9e10-2ab07e3e9b4c','Женский');

insert into magic_colour (id, name) values ('9d53746d-c930-464a-a750-fb77ef4c13ec', 'White');
insert into magic_colour (id, name) values ('6f7862d8-cff0-4033-ae46-9a0923af6728', 'Black');
insert into magic_colour (id, name) values ('1075c7ce-4982-4987-90d4-177a6e98602b', 'Purple');

insert into magic_power (id, name) values ('02fca322-e58a-44a8-a1d4-3caed2007b89', 'Weak');
insert into magic_power (id, name) values ('495ad606-4aa9-4af8-be22-f6b3b6f9e358', 'Strong');
insert into magic_power (id, name) values ('b1a7e6ae-d5f5-40a4-8421-730403ba101d', 'Powerful');

insert into magic_type (id, name) values ('f1046ff8-9014-4041-bdb3-650bb89fcce5', 'Kind');
insert into magic_type (id, name) values ('131785d0-80b8-4203-be98-16a8526540f5', 'Evil');
insert into magic_type (id, name) values ('c99f5672-bf7d-4d0b-952e-c9d59ba8e6f8', 'Neutral');

insert into magic_state (id, name) values ('4fce0448-6786-4fc1-b673-a7d78ebf7242', 'Liquid');
insert into magic_state (id, name) values ('95732eae-c77f-4d12-9e34-fa54a00f068b', 'Crystalline');

-- Insert Magic
insert into magic (id, magic_colour_id, magic_power_id, magic_type_id, magic_state_id)
values ('3b626072-75cf-4c2c-9973-8d14febd7bec', '9d53746d-c930-464a-a750-fb77ef4c13ec', '02fca322-e58a-44a8-a1d4-3caed2007b89', 'f1046ff8-9014-4041-bdb3-650bb89fcce5', '4fce0448-6786-4fc1-b673-a7d78ebf7242');
insert into magic (id, magic_colour_id, magic_power_id, magic_type_id, magic_state_id)
values ('586b95c5-294f-4b67-9c28-a133ea9d91ce', '1075c7ce-4982-4987-90d4-177a6e98602b', 'b1a7e6ae-d5f5-40a4-8421-730403ba101d', 'c99f5672-bf7d-4d0b-952e-c9d59ba8e6f8', '95732eae-c77f-4d12-9e34-fa54a00f068b');
insert into magic (id, magic_colour_id, magic_power_id, magic_type_id, magic_state_id)
values ('fea22200-2cc6-4b11-909c-cab182d15fc9', '6f7862d8-cff0-4033-ae46-9a0923af6728', '495ad606-4aa9-4af8-be22-f6b3b6f9e358', '131785d0-80b8-4203-be98-16a8526540f5', '95732eae-c77f-4d12-9e34-fa54a00f068b');

-- Insert Magic_Storage
insert into magic_storage (id, volume, magic_id)
values ('acb64a1e-6d31-41c0-b824-4512e8968af3', 100, '3b626072-75cf-4c2c-9973-8d14febd7bec');
insert into magic_storage (id, volume, magic_id)
values ('9c6a0d8b-37bf-4610-b8a1-c72585e59adf', 0, '586b95c5-294f-4b67-9c28-a133ea9d91ce');
insert into magic_storage (id, volume, magic_id)
values ('844b3176-e70d-468e-b70e-8f4521a983cd', 15, 'fea22200-2cc6-4b11-909c-cab182d15fc9');


-- Insert Animals
insert into animals (id, name, magic_volume, magic_id)
values ('5a76436c-4ab8-405f-82fd-1e07943260b2', 'The бык', 70, '3b626072-75cf-4c2c-9973-8d14febd7bec');
insert into animals (id, name, magic_volume, magic_id)
values ('ae642764-e158-4c8b-a0b5-ad2b768b7d2b', 'Ёж топотун', 15, '586b95c5-294f-4b67-9c28-a133ea9d91ce');
insert into animals (id, name, magic_volume, magic_id)
values ('b5b8eb7b-c7a1-41a1-926c-ec5cfa86894d', 'Черепаха Скорость', 20, 'fea22200-2cc6-4b11-909c-cab182d15fc9');
insert into animals (id, name, magic_volume, magic_id)
values ('3b8d051f-8ea6-4f7d-a5b0-60bf197dd416', 'Дикий лось', 65, 'fea22200-2cc6-4b11-909c-cab182d15fc9');

-- Insert AnimalStorage
insert into animal_storage (id, quantity, animal_id)
values ('20947e86-de6e-49fb-bca0-b41bf851060a', 7, '5a76436c-4ab8-405f-82fd-1e07943260b2');
insert into animal_storage (id, quantity, animal_id)
values ('fb887fc6-6c1e-4f01-8d87-b13630a4d430', 15, 'ae642764-e158-4c8b-a0b5-ad2b768b7d2b');
insert into animal_storage (id, quantity, animal_id)
values ('c70164bd-e546-45f4-88d8-070c639ebdbb', 1, 'b5b8eb7b-c7a1-41a1-926c-ec5cfa86894d');
insert into animal_storage (id, quantity, animal_id)
values ('02ae2f98-7287-457a-9545-13be841652a7', 4, '3b8d051f-8ea6-4f7d-a5b0-60bf197dd416');

-- Insert 1 Magician
insert into users (id, email, activation_code, password, role, name, surname, patronymic, birth_date, reward_points, penalty_points, sex_id)
values ('71b0af32-887c-4903-bc54-af3f96481e9e','magician@mail.ru', 'true', '$2a$12$2xHLqWTz63INeUGlIo4U/.tOctTfyYEe41NpMti/mlG7v8wR3DW8K', 'MAGICIAN', 'Magician', 'Magicianov', 'Magicianovich', '2002-10-28', 0, 0, 'c99ccd51-5731-42a3-9cfc-31cc4011e035');
insert into magicians (id) values ('71b0af32-887c-4903-bc54-af3f96481e9e');

-- Insert 1 Storekeeper
insert into users (id, email, activation_code, password, role, name, surname, patronymic, birth_date, reward_points, penalty_points, sex_id)
values ('72b0af32-887c-4903-bc54-af3f96481e9e','storekeeper1@mail.ru', 'true', '$2a$12$2xHLqWTz63INeUGlIo4U/.tOctTfyYEe41NpMti/mlG7v8wR3DW8K', 'STOREKEEPER', 'Storekeeper', 'Storekeeperov', 'Storekeeperovich', '2002-10-29', 0, 0, '848f4054-a9c1-4525-9e10-2ab07e3e9b4c');
insert into storekeepers (id) values ('72b0af32-887c-4903-bc54-af3f96481e9e');

-- Insert 1 Hunter
insert into users (id, email, activation_code, password, role, name, surname, patronymic, birth_date, reward_points, penalty_points, sex_id)
values ('73b0af32-887c-4903-bc54-af3f96481e9e','hunter1@mail.ru', 'true', '$2a$12$2xHLqWTz63INeUGlIo4U/.tOctTfyYEe41NpMti/mlG7v8wR3DW8K', 'HUNTER', 'Hunter', 'Hunterov', 'Hunterovich', '2002-10-30', 0, 0, 'c99ccd51-5731-42a3-9cfc-31cc4011e035');
insert into hunters (id) values ('73b0af32-887c-4903-bc54-af3f96481e9e');

-- Insert 1 Extractor
insert into users (id, email, activation_code, password, role, name, surname, patronymic, birth_date, reward_points, penalty_points, sex_id)
values ('74b0af32-887c-4903-bc54-af3f96481e9e','extractor1@mail.ru', 'true', '$2a$12$2xHLqWTz63INeUGlIo4U/.tOctTfyYEe41NpMti/mlG7v8wR3DW8K', 'EXTRACTOR', 'Extractor', 'Extractorov', 'Extractorovich', '2002-10-31', 0, 0, 'c99ccd51-5731-42a3-9cfc-31cc4011e035');
insert into extractors (id) values ('74b0af32-887c-4903-bc54-af3f96481e9e');