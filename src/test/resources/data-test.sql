DROP
    DATABASE IF EXISTS fibersmartV2_test;

CREATE
    DATABASE fibersmartV2_test CHARACTER SET utf8mb4;

USE
    fibersmartV2_test;

CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    username   VARCHAR(125)          NOT NULL,
    first_name VARCHAR(125)          NOT NULL,
    last_name  VARCHAR(125)          NOT NULL,
    password   VARCHAR(125)          NOT NULL,
    enabled    BOOLEAN               NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE roles
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    role_type VARCHAR(125),
    PRIMARY KEY (id)
);

CREATE TABLE users_roles
(
    user_id     BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE rolepermissions
(
    id     BIGINT NOT NULL,
    permission VARCHAR(125),
    PRIMARY KEY (id)
);

CREATE TABLE roles_rolepermissions
(
    role_id     BIGINT,
    rolepermission_id BIGINT,
    PRIMARY KEY (role_id, rolepermission_id)
);

CREATE TABLE groups
(
    id     BIGINT NOT NULL,
    group_name VARCHAR(125),
    is_activated BOOLEAN,
    PRIMARY KEY (id)
);

CREATE TABLE users_groups
(
    user_id     BIGINT,
    group_id BIGINT,
    PRIMARY KEY (user_id, group_id)
);

CREATE TABLE grouppermissions
(
    id     BIGINT NOT NULL,
    permission VARCHAR(125),
    PRIMARY KEY (id)
);

CREATE TABLE groups_grouppermissions
(
    group_id     BIGINT,
    grouppermission_id BIGINT,
    PRIMARY KEY (group_id, grouppermission_id)
);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_users_roles_users
        FOREIGN KEY (user_id)
            REFERENCES users (id);

ALTER TABLE users_roles
    ADD CONSTRAINT fk_fk_users_roles_roles
        FOREIGN KEY (role_id)
            REFERENCES roles (id);

ALTER TABLE roles_rolepermissions
    ADD CONSTRAINT fk_roles_rolepermissions_roles
        FOREIGN KEY (role_id)
            REFERENCES roles (id);

ALTER TABLE roles_rolepermissions
    ADD CONSTRAINT fk_roles_rolepermissions_rolepermissions
        FOREIGN KEY (rolepermission_id)
            REFERENCES rolepermissions (id);

ALTER TABLE groups_grouppermissions
    ADD CONSTRAINT fk_groups_grouppermissions_groups
        FOREIGN KEY (group_id)
            REFERENCES groups (id);

ALTER TABLE groups_grouppermissions
    ADD CONSTRAINT fk_groups_grouppermissions_grouppermissions
        FOREIGN KEY (grouppermission_id)
            REFERENCES grouppermissions (id);

ALTER TABLE users_groups
    ADD CONSTRAINT fk_users_groups_users
        FOREIGN KEY (user_id)
            REFERENCES users (id);

ALTER TABLE users_groups
    ADD CONSTRAINT fk_users_groups_groups
        FOREIGN KEY (group_id)
            REFERENCES groups (id);


INSERT INTO users
VALUES (1, "admin", "admin", "admin", "$2y$10$hOGY3ZnLb2hXQJAUVi2aCOLsW9oCdmPyJzoKWe9hwNTeliDzpcyo6", 1),
       (2, "standard", "standard", "standard", "$2y$10$2r7ZDlWH/aKDEASqoDMjtOL2qfVawfDVSOu1fA2Fl0/3lshKCAEJW", 1);

INSERT INTO roles
VALUES (1, "ROLE_ADMIN"),
       (2, "ROLE_STANDARD");

INSERT INTO users_roles
VALUES (1, 1),
       (2, 2);

INSERT INTO rolepermissions
VALUES (1, "MANAGE_USER");

INSERT INTO roles_rolepermissions
VALUES (1, 1);

INSERT INTO groups
VALUES (1, "GV", true);

INSERT INTO users_groups
VALUES (1, 1);

INSERT INTO grouppermissions
VALUES (1, "SEE_ALL_SLIDE");

INSERT INTO groups_grouppermissions
VALUES (1, 1);