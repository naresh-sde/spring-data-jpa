-- ============================================================
--  Sample data (runs after schema.sql, see
--  spring.jpa.defer-datasource-initialization=true).
--  Department ids are assigned by H2 in insert order (1,2,3);
--  employees pick them up with a subquery instead of hardcoding.
-- ============================================================

INSERT INTO departments (name, location) VALUES ('Engineering', 'Bangalore');
INSERT INTO departments (name, location) VALUES ('HR', 'Pune');
INSERT INTO departments (name, location) VALUES ('Sales', 'Mumbai');

INSERT INTO employees (name, email, salary, joined_on, department_id)
SELECT 'Asha', 'asha@engineering.com', 95000.00, DATE '2022-01-10', id
FROM departments WHERE name = 'Engineering';

INSERT INTO employees (name, email, salary, joined_on, department_id)
SELECT 'Ravi', 'ravi@engineering.com', 78000.00, DATE '2023-03-01', id
FROM departments WHERE name = 'Engineering';

INSERT INTO employees (name, email, salary, joined_on, department_id)
SELECT 'Meera', 'meera@hr.com', 62000.00, DATE '2021-06-15', id
FROM departments WHERE name = 'HR';
