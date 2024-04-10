-- liquibase formatted sql

-- changeset mikhail-lukichev:1
CREATE TABLE avatar
(
    id serial primary key,
    file_size bigint,
    media_type character varying,
    data bytea,
    author_id integer
);