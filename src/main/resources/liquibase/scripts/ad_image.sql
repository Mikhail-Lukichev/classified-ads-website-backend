-- liquibase formatted sql

-- changeset mikhail-lukichev:1
CREATE TABLE ad_image
(
    id serial primary key,
    file_path character varying,
    file_size bigint,
    media_type character varying,
    ad_id integer
);