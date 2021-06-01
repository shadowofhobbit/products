-- changeset julia:1
CREATE TABLE products (id BIGSERIAL NOT NULL PRIMARY KEY, title VARCHAR, description TEXT, price NUMERIC, created_at DATE, updated_at DATE);
