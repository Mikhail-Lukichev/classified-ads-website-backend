-- liquibase formatted sql

-- changeset mikhail-lukichev:1
CREATE TABLE comment
(
    id serial primary key,
    author_id serial,
    ad_id serial,
    created_at bigint,
    text character varying
);