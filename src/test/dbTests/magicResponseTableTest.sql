SELECT id FROM magic_responses;

INSERT INTO magic_responses (id, response_date, storekeeper_id, magic_application_id)
VALUES ('b4c8418b-67b6-4f55-96e4-1d65542abbe4', '2025-10-28', '72b0af32-887c-4903-bc54-af3f96481e9e', '33e27303-e74e-4b27-bd5f-f268ae6b8bdf');

INSERT INTO magic_responses (id, response_date, storekeeper_id, magic_application_id)
VALUES ('c3c8418b-67b6-4f55-96e4-1d65542abbe4', '2025-10-28', '72b0af32-887c-4903-bc54-af3f96481e9e', '33e27303-e74e-4b27-bd5f-f268ae6b8bdf');

SELECT * FROM magic_responses WHERE id='c3c8418b-67b6-4f55-96e4-1d65542abbe4';


INSERT INTO magic_responses (id, response_date, storekeeper_id, magic_application_id)
VALUES ('d3c8418b-67b6-4f55-96e4-1d65542abbe4', '2025-10-28', null, '33e27303-e74e-4b27-bd5f-f268ae6b8bdf');

INSERT INTO magic_responses (id, response_date, storekeeper_id, magic_application_id)
VALUES ('d3c8418b-67b6-4f55-96e4-1d65542abbe4', '2025-10-28', '72b0af32-887c-4903-bc54-af3f96481e9e', '33e27303-e74e-4b27-bd5f-f268ae6b8bdf');

SELECT * FROM magic_responses WHERE id='d3c8418b-67b6-4f55-96e4-1d65542abbe4';