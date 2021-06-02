--liquibase formatted sql
--changeset julia:1
CREATE TABLE products (id BIGSERIAL NOT NULL PRIMARY KEY, title VARCHAR, description TEXT, price NUMERIC, created_at DATE, updated_at DATE);

--changeset julia:2
CREATE TABLE prices (currency VARCHAR NOT NULL, price NUMERIC, product_id BIGINT REFERENCES products(id), PRIMARY KEY (currency, product_id));

--changeset julia:3
ALTER TABLE products DROP COLUMN price;
