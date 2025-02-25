--liquibase formatted sql
--changeset Damir Nurlygayanov:FIT-1 localFilePath:01.000.00/user_profile.sql

CREATE TABLE user_profile (
   profile_id            UUID                      NOT NULL PRIMARY KEY,
   user_id               UUID                      NOT NULL,
   firstname             VARCHAR(255)              NOT NULL,
   lastname              VARCHAR(255)              NOT NULL,
   birthdate             DATE                      NOT NULL,

   FOREIGN KEY (user_id) REFERENCES users(user_id)
);