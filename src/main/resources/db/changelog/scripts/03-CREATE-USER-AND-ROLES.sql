CREATE TABLE cars.role
(
    ID BIGINT NOT NULL PRIMARY KEY,
    NAME VARCHAR(50) UNIQUE NOT NULL

);

CREATE TABLE cars.app_user
(
    ID BIGINT NOT NULL PRIMARY KEY,
    USERNAME VARCHAR(100) UNIQUE NOT NULL,
    PASSWORD VARCHAR NOT NULL


);


CREATE TABLE cars.user_role(
    USER_ID BIGINT NOT NULL,
    ROLE_ID BIGINT NOT NULL,
    PRIMARY KEY (USER_ID,ROLE_ID),
    FOREIGN KEY (USER_ID) REFERENCES APP_USER(ID),
    FOREIGN KEY (ROLE_ID) REFERENCES ROLE(ID)

);

CREATE SEQUENCE cars.user_seq
     INCREMENT 1
     START 1000;