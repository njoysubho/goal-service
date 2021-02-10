create schema if not exists goal;
SET search_path = goal, pg_catalog;

CREATE TABLE IF NOT EXISTS Category(
    id UUID PRIMARY KEY,
    category_name VARCHAR(255),
    created_by VARCHAR(255) ,
    created_on TIMESTAMP,
    system_defined BOOLEAN DEFAULT FALSE,
    modified_on TIMESTAMP,
    tags VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Goal(
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    created_by VARCHAR(255) ,
    created_on TIMESTAMP,
    system_defined BOOLEAN DEFAULT FALSE,
    modified_on TIMESTAMP,
    category_id UUID  references Category(id)
);