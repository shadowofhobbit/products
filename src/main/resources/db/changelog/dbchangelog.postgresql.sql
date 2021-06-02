--liquibase formatted sql
--changeset julia:1
CREATE TABLE products (id BIGSERIAL NOT NULL PRIMARY KEY, title VARCHAR, description TEXT, price NUMERIC, created_at DATE, updated_at DATE);

--changeset julia:2
CREATE TABLE prices (currency VARCHAR NOT NULL, price NUMERIC, product_id BIGINT REFERENCES products(id), PRIMARY KEY (currency, product_id));

--changeset julia:3
ALTER TABLE products DROP COLUMN price;

--changeset julia:4
CREATE TABLE details (language VARCHAR NOT NULL, title VARCHAR, description TEXT, product_id BIGINT REFERENCES products(id), PRIMARY KEY (language, product_id));

--changeset julia:5
ALTER TABLE products DROP COLUMN title, DROP COLUMN description;
