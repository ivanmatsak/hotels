--liquibase formatted sql

--changeset author:3

ALTER TABLE address ALTER COLUMN id RESTART WITH 5;
ALTER TABLE hotel ALTER COLUMN id RESTART WITH 5;
ALTER TABLE amenity ALTER COLUMN id RESTART WITH 10;