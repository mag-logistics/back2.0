CREATE TABLE sex
(
    id   varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    name varchar(10)
);

CREATE TABLE users
(
    id              varchar(36)   NOT NULL PRIMARY KEY UNIQUE,
    email           varchar(250)  NOT NULL UNIQUE,
    activation_code varchar(100),
    password        varchar(1000) NOT NULL,
    role            varchar(25),
    name            varchar(250),
    surname         varchar(250),
    patronymic      varchar(250),
    birth_date      date,
    sex_id          varchar(25),
    FOREIGN KEY (sex_id) REFERENCES sex (id)
);

CREATE TABLE storekeepers
(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    FOREIGN KEY (id) REFERENCES users (id)
);

CREATE TABLE magicians
(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    FOREIGN KEY (id) REFERENCES users (id)
);

CREATE TABLE extractors
(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    FOREIGN KEY (id) REFERENCES users (id)
);

CREATE TABLE hunters
(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    FOREIGN KEY (id) REFERENCES users (id)
);

CREATE TABLE magic
(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    magic_power_id varchar(36) NOT NULL,
    magic_state_id varchar(36) NOT NULL,
    magic_colour_id varchar(36) NOT NULL,
    magic_type_id varchar(36) NOT NULL
);

CREATE TABLE animals
(
    id           varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    name         varchar(50) NOT NULL UNIQUE,
    magic_volume int,
    magic_id     varchar(36),
    FOREIGN KEY (magic_id) REFERENCES magic (id)
);

CREATE TABLE animal_storage
(
    id        varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    quantity  int         NOT NULL,
    animal_id varchar(36) NOT NULL,
    FOREIGN KEY (animal_id) REFERENCES animals (id)
);

-- CREATE TABLE application_status
-- (
--     id   varchar(36) NOT NULL PRIMARY KEY UNIQUE,
--     name varchar(15) NOT NULL UNIQUE
-- );

CREATE TABLE extraction_response
(
    id            varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    extractor_id  varchar(36) NOT NULL,
    response_date date
);

CREATE TABLE extraction_applications
(
    id                     varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    volume                 int         NOT NULL,
    init_date              date        NOT NULL,
    deadline               date        NOT NULL,
    status_id              varchar(36) NOT NULL,
    extraction_response_id varchar(15),
    storekeeper_id         varchar(36) NOT NULL,
    magic_id               varchar(36) NOT NULL,
--     FOREIGN KEY (status_id) REFERENCES application_status (id),
    FOREIGN KEY (extraction_response_id) REFERENCES extraction_response (id),
    FOREIGN KEY (storekeeper_id) REFERENCES storekeepers (id),
    FOREIGN KEY (magic_id) REFERENCES magic (id)
);

CREATE TABLE hunter_response
(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    hunter_id  varchar(36) NOT NULL,
    response_date date,
    volume int
);

CREATE TABLE hunter_application
(
    id                 varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    animal_count       int         NOT NULL,
    init_date          date        NOT NULL,
    deadline           date        NOT NULL,
    animal_id          varchar(36) NOT NULL,
    status_id          varchar(36) NOT NULL,
    extractor_id       varchar(36) NOT NULL,
    magic_id           varchar(36) NOT NULL,
    extraction_app_id  varchar(36) NOT NULL,
    hunter_response_id varchar(36) NOT NULL,
    FOREIGN KEY (animal_id) REFERENCES animals (id),
    FOREIGN KEY (extractor_id) REFERENCES extractors (id),
    FOREIGN KEY (magic_id) REFERENCES magic (id),
    FOREIGN KEY (extraction_app_id) REFERENCES extraction_applications (id),
    FOREIGN KEY (hunter_response_id) REFERENCES hunter_response (id)
);

CREATE TABLE magic_responses
(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    response_date date,
    storekeeper_id varchar(36) NOT NULL,
    FOREIGN KEY (storekeeper_id) REFERENCES storekeepers (id)
);

CREATE TABLE magic_applications
(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    volume             int         NOT NULL,
    init_date          date        NOT NULL,
    deadline           date        NOT NULL,
    status_id          varchar(36) NOT NULL,
    extraction_app_id       varchar(36) NOT NULL,
    magic_id           varchar(36) NOT NULL,
    magic_response_id  varchar(36) NOT NULL,
    magician_id varchar(36) NOT NULL,
--     FOREIGN KEY (status_id) REFERENCES application_status (id),
    FOREIGN KEY (extraction_app_id) REFERENCES extraction_applications (id),
    FOREIGN KEY (magic_id) REFERENCES magic (id),
    FOREIGN KEY (magic_response_id) REFERENCES magic_responses (id),
    FOREIGN KEY (magician_id) REFERENCES magicians (id)
);

CREATE TABLE magic_colour
(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    name varchar(30) NOT NULL UNIQUE
);

CREATE TABLE magic_power
(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    name varchar(30) NOT NULL UNIQUE
);

CREATE TABLE magic_state
(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    name varchar(30) NOT NULL UNIQUE
);

CREATE TABLE magic_storage
(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    volume int NOT NULL,
    magic_id varchar(36) NOT NULL UNIQUE,
    FOREIGN KEY (magic_id) REFERENCES magic (id)
);

CREATE TABLE magic_type
(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    name varchar(30) NOT NULL UNIQUE
);

CREATE TABLE refresh_token
(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    user_id varchar(36),
    token varchar(300),
    expiry_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);


