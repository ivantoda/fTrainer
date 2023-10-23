CREATE TABLE IF NOT EXISTS role (
    id SERIAL NOT NULL PRIMARY KEY,
    "name" VARCHAR NOT NULL
);

INSERT INTO role ("name") VALUES ('ADMIN');
INSERT INTO role ("name") VALUES ('CLIENT');
INSERT INTO role ("name") VALUES ('TRAINER');

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    user_role role,
    is_enabled BOOLEAN NOT NULL
);
