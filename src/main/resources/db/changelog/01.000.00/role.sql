--liquibase formatted sql
--changeset Damir Nurlygayanov:FIT-1 localFilePath:01.000.00/role.sql

CREATE TABLE role (
  id                SERIAL                    NOT NULL PRIMARY KEY,
  name              VARCHAR(255)              NOT NULL
);