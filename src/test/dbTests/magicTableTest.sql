SELECT id FROM magic;

INSERT INTO magic (id, magic_power_id, magic_state_id, magic_colour_id, magic_type_id)
values ('3b626072-75cf-4c2c-9973-8d14febd7bec', '9d53746d-c930-464a-a750-fb77ef4c13ec', '02fca322-e58a-44a8-a1d4-3caed2007b89', 'f1046ff8-9014-4041-bdb3-650bb89fcce5', '4fce0448-6786-4fc1-b673-a7d78ebf7242');

INSERT INTO magic (id, magic_power_id, magic_state_id, magic_colour_id, magic_type_id)
values ('3b626072-75cf-4c2c-9973-8d14febdacec', '9d53746d-c930-464a-a750-fb77ef4c13ec', '02fca322-e58a-44a8-a1d4-3caed2007b89', 'f1046ff8-9014-4041-bdb3-650bb89fcce5', '4fce0448-6786-4fc1-b673-a7d78ebf7242');

SELECT * FROM magic WHERE id='3b626072-75cf-4c2c-9973-8d14febdacec';


INSERT INTO magic (id, magic_power_id, magic_state_id, magic_colour_id, magic_type_id)
values ('4d626072-75cf-4c2c-9973-8d14febdacec', null, null, null, null);

INSERT INTO magic (id, magic_power_id, magic_state_id, magic_colour_id, magic_type_id)
values ('4d626072-75cf-4c2c-9973-8d14febdacec', '9d53746d-c930-464a-a750-fb77ef4c13ec', '02fca322-e58a-44a8-a1d4-3caed2007b89', 'f1046ff8-9014-4041-bdb3-650bb89fcce5', '4fce0448-6786-4fc1-b673-a7d78ebf7242');

SELECT * FROM magic WHERE id='4d626072-75cf-4c2c-9973-8d14febdacec';