SET search_path = goal, pg_catalog;

CREATE TABLE Category (
    id SERIAL PRIMARY KEY,
    category_name VARCHAR,
    created_by UUID ,
    created_on TIMESTAMP,
    system_defined BOOLEAN,
    modified_on TIMESTAMP,
    tags VARCHAR
);