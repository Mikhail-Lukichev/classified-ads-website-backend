-- liquibase formatted sql

-- changeset mikhail-lukichev:1
CREATE TABLE author
(
    id serial primary key,
    email character varying,
    password character varying,
    first_name character varying,
    last_name character varying,
    phone character varying,
    role character varying
);