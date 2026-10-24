-- ============================================================
--  Full DDL for the JPA demo (H2 in-memory).
--  ddl-auto=none, so these scripts own the schema.
--  Column names must match the @Entity mappings:
--    Department -> departments, Employee -> employees
-- ============================================================

CREATE TABLE departments (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100)  NOT NULL UNIQUE,
    location   VARCHAR(150),
    created_at TIMESTAMP     DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE employees (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(100)  NOT NULL,
    email         VARCHAR(150)  NOT NULL UNIQUE,
    salary        DECIMAL(12,2) NOT NULL,
    joined_on     DATE,
    department_id BIGINT        NOT NULL,
    version       BIGINT        NOT NULL DEFAULT 0,
    created_at    TIMESTAMP     DEFAULT CURRENT_TIMESTAMP
);

-- 1:N relationship - the FK lives on the child (many) side
ALTER TABLE employees
    ADD CONSTRAINT fk_employees_department FOREIGN KEY (department_id) REFERENCES departments (id);
