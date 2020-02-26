create schema if not exists goal;
SET search_path = goal, pg_catalog;

CREATE TABLE IF NOT EXISTS goal.Category(
    id UUID PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL,
    created_by UUID ,
    created_on TIMESTAMP,
    system_defined BOOLEAN DEFAULT FALSE,
    modified_on TIMESTAMP,
    tags VARCHAR(255)
);