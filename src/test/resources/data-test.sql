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
    PRIMARY KEY (id)
);

CREATE TABLE userroles
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    role_type VARCHAR(125),
    PRIMARY KEY (id)
);

CREATE TABLE users_userroles
(
    user_id     BIGINT NOT NULL,
    userrole_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, userrole_id)
);

CREATE TABLE rolepermissions
(
    id     BIGINT NOT NULL,
    permission VARCHAR(125),
    PRIMARY KEY (id)
);

CREATE TABLE userroles_rolepermissions
(
    userrole_id     BIGINT,
    rolepermission_id BIGINT,
    PRIMARY KEY (userrole_id, rolepermission_id)
);

CREATE TABLE usergroups
(
    id     BIGINT NOT NULL,
    group_name VARCHAR(125),
    is_activated BOOLEAN,
    PRIMARY KEY (id)
);

CREATE TABLE users_usergroups
(
    user_id     BIGINT,
    usergroup_id BIGINT,
    PRIMARY KEY (user_id, usergroup_id)
);

CREATE TABLE grouppermissions
(
    id     BIGINT NOT NULL,
    permission VARCHAR(125),
    PRIMARY KEY (id)
);

CREATE TABLE usergroups_grouppermissions
(
    usergroup_id     BIGINT,
    grouppermission_id BIGINT,
    PRIMARY KEY (usergroup_id, grouppermission_id)
);

ALTER TABLE users_userroles
    ADD CONSTRAINT fk_users_userroles_users
        FOREIGN KEY (user_id)
            REFERENCES users (id);

ALTER TABLE users_userroles
    ADD CONSTRAINT fk_fk_users_userroles_userroles
        FOREIGN KEY (userrole_id)
            REFERENCES userroles (id);

ALTER TABLE userroles_rolepermissions
    ADD CONSTRAINT fk_userroles_rolepermissions_userroles
        FOREIGN KEY (userrole_id)
            REFERENCES userroles (id);

ALTER TABLE userroles_rolepermissions
    ADD CONSTRAINT fk_userroles_rolepermissions_rolepermissions
        FOREIGN KEY (rolepermission_id)
            REFERENCES rolepermissions (id);

ALTER TABLE usergroups_grouppermissions
    ADD CONSTRAINT fk_usergroups_grouppermissions_usergroups
        FOREIGN KEY (usergroup_id)
            REFERENCES usergroups (id);

ALTER TABLE usergroups_grouppermissions
    ADD CONSTRAINT fk_usergroups_grouppermissions_grouppermissions
        FOREIGN KEY (grouppermission_id)
            REFERENCES grouppermissions (id);

ALTER TABLE users_usergroups
    ADD CONSTRAINT fk_users_usergroups_users
        FOREIGN KEY (user_id)
            REFERENCES users (id);

ALTER TABLE users_usergroups
    ADD CONSTRAINT fk_users_usergroups_usergroups
        FOREIGN KEY (usergroup_id)
            REFERENCES usergroups (id);


INSERT INTO users
VALUES (1, "admin", "admin", "admin", "$2y$10$hOGY3ZnLb2hXQJAUVi2aCOLsW9oCdmPyJzoKWe9hwNTeliDzpcyo6"),
       (2, "standard", "standard", "standard", "$2y$10$2r7ZDlWH/aKDEASqoDMjtOL2qfVawfDVSOu1fA2Fl0/3lshKCAEJW");

INSERT INTO userroles
VALUES (1, "ROLE_ADMIN"),
       (2, "ROLE_STANDARD");

INSERT INTO users_userroles
VALUES (1, 1),
       (2, 2);

INSERT INTO rolepermissions
VALUES (1, "MANAGE_USER");

INSERT INTO userroles_rolepermissions
VALUES (1, 1);

INSERT INTO usergroups
VALUES (1, "GV", true);

INSERT INTO users_usergroups
VALUES (1, 1);

INSERT INTO grouppermissions
VALUES (1, "SEE_ALL_SLIDE");

INSERT INTO usergroups_grouppermissions
VALUES (1, 1);