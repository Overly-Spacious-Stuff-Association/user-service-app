--liquibase formatted sql
--changeset Damir Nurlygayanov:FIT-1 localFilePath:01.000.00/users.sql

CREATE TABLE users (
  user_id               UUID                      NOT NULL PRIMARY KEY,
  password_encoded      VARCHAR(255)              NOT NULL,
  email                 VARCHAR(255)              UNIQUE NOT NULL,
  role_id               INTEGER                   NOT NULL,
  created_at            TIMESTAMP(0)              NOT NULL DEFAULT now(),

  FOREIGN KEY (role_id) REFERENCES role(id)
);
