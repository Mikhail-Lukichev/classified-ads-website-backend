-- liquibase formatted sql

-- changeset mikhail-lukichev:1
CREATE TABLE ad
(
    id serial primary key,
    author_id serial,
    price integer,
    title character varying,
    description character varying
);