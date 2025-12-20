CREATE TABLE magic_app_patterns
(
    id varchar(36) NOT NULL PRIMARY KEY UNIQUE,
    volume             int         NOT NULL,
    deadline           date        NOT NULL,
    magic_id           varchar(36) NOT NULL,
    magician_id varchar(36) NOT NULL,
    FOREIGN KEY (magic_id) REFERENCES magic (id),
    FOREIGN KEY (magician_id) REFERENCES magicians (id)
);