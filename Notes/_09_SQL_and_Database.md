# SQL & Database Testing — Complete Reference Guide

---

## Table of Contents
1. [SQL Fundamentals](#1-sql-fundamentals)
2. [SELECT & Filtering](#2-select--filtering)
3. [Aggregate Functions & GROUP BY](#3-aggregate-functions--group-by)
4. [JOINs](#4-joins)
5. [Subqueries & CTEs](#5-subqueries--ctes)
6. [Data Manipulation (INSERT, UPDATE, DELETE)](#6-data-manipulation-insert-update-delete)
7. [DDL — CREATE, ALTER, DROP](#7-ddl--create-alter-drop)
8. [Indexes](#8-indexes)
9. [Constraints](#9-constraints)
10. [Stored Procedures & Functions](#10-stored-procedures--functions)
11. [Transactions & ACID](#11-transactions--acid)
12. [Window Functions](#12-window-functions)
13. [Database Testing Concepts](#13-database-testing-concepts)
14. [JDBC in Java](#14-jdbc-in-java)
15. [Interview Q&A](#15-interview-qa)

---

## 1. SQL Fundamentals

### SQL Categories

| Category | Commands | Purpose |
|---------|----------|---------|
| **DDL** (Data Definition) | `CREATE`, `ALTER`, `DROP`, `TRUNCATE`, `RENAME` | Define schema |
| **DML** (Data Manipulation) | `SELECT`, `INSERT`, `UPDATE`, `DELETE` | Manipulate data |
| **DCL** (Data Control) | `GRANT`, `REVOKE` | Permissions |
| **TCL** (Transaction Control) | `COMMIT`, `ROLLBACK`, `SAVEPOINT` | Transactions |

### Sample Schema (used throughout)

```sql
CREATE TABLE users (
    user_id    INT PRIMARY KEY AUTO_INCREMENT,
    username   VARCHAR(50) NOT NULL UNIQUE,
    email      VARCHAR(100) NOT NULL,
    role       VARCHAR(20) DEFAULT 'customer',
    status     BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
    product_id   INT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(100) NOT NULL,
    category     VARCHAR(50),
    price        DECIMAL(10,2) NOT NULL,
    stock        INT DEFAULT 0
);

CREATE TABLE orders (
    order_id    INT PRIMARY KEY AUTO_INCREMENT,
    user_id     INT NOT NULL,
    product_id  INT NOT NULL,
    quantity    INT NOT NULL,
    total_price DECIMAL(10,2),
    order_date  DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (user_id)    REFERENCES users(user_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
```

---

## 2. SELECT & Filtering

### Basic SELECT

```sql
-- All columns
SELECT * FROM users;

-- Specific columns
SELECT username, email, role FROM users;

-- With alias
SELECT username AS name, email AS contact FROM users;

-- Distinct values
SELECT DISTINCT role FROM users;

-- Limit rows
SELECT * FROM users LIMIT 10;
SELECT * FROM users LIMIT 10 OFFSET 20;   -- skip 20, take 10

-- Order
SELECT * FROM products ORDER BY price ASC;
SELECT * FROM products ORDER BY price DESC, name ASC;

-- Arithmetic
SELECT name, price, price * 1.1 AS price_with_tax FROM products;

-- String functions
SELECT UPPER(username), LOWER(email), LENGTH(username) FROM users;
SELECT CONCAT(first_name, ' ', last_name) AS full_name FROM users;
SELECT SUBSTRING(email, 1, LOCATE('@', email)-1) AS email_prefix FROM users;
SELECT TRIM('  hello  ');
SELECT REPLACE(email, '@gmail.com', '@company.com') FROM users;
```

### WHERE Clause

```sql
-- Basic conditions
SELECT * FROM users WHERE role = 'admin';
SELECT * FROM users WHERE status = TRUE AND role = 'customer';
SELECT * FROM products WHERE price > 50 OR stock = 0;
SELECT * FROM users WHERE NOT status = TRUE;

-- IN / NOT IN
SELECT * FROM users WHERE role IN ('admin', 'manager');
SELECT * FROM products WHERE category NOT IN ('Electronics', 'Books');

-- BETWEEN
SELECT * FROM products WHERE price BETWEEN 10 AND 100;
SELECT * FROM orders WHERE order_date BETWEEN '2024-01-01' AND '2024-12-31';

-- LIKE patterns
SELECT * FROM users WHERE username LIKE 'admin%';      -- starts with 'admin'
SELECT * FROM users WHERE email LIKE '%@gmail.com';    -- ends with '@gmail.com'
SELECT * FROM products WHERE name LIKE '%phone%';      -- contains 'phone'
SELECT * FROM users WHERE username LIKE '_sername';    -- _ = single any char

-- NULL checks
SELECT * FROM users WHERE email IS NULL;
SELECT * FROM products WHERE stock IS NOT NULL;

-- CASE expression
SELECT username,
    CASE
        WHEN role = 'admin'   THEN 'Super User'
        WHEN role = 'manager' THEN 'Power User'
        ELSE                       'Regular User'
    END AS user_type
FROM users;
```

---

## 3. Aggregate Functions & GROUP BY

```sql
-- Aggregate functions
SELECT COUNT(*)           FROM users;                    -- total rows
SELECT COUNT(DISTINCT role) FROM users;                  -- distinct values
SELECT SUM(total_price)   FROM orders;                   -- sum
SELECT AVG(price)         FROM products;                 -- average
SELECT MAX(price)         FROM products;                 -- maximum
SELECT MIN(price)         FROM products;                 -- minimum

-- GROUP BY
SELECT role, COUNT(*) AS user_count
FROM users
GROUP BY role;

SELECT category, AVG(price) AS avg_price, COUNT(*) AS product_count
FROM products
GROUP BY category
ORDER BY avg_price DESC;

-- HAVING — filter on aggregated result (WHERE can't use aggregate functions)
SELECT user_id, COUNT(*) AS order_count
FROM orders
GROUP BY user_id
HAVING COUNT(*) > 5;           -- only users with more than 5 orders

SELECT category, SUM(price * stock) AS inventory_value
FROM products
GROUP BY category
HAVING SUM(price * stock) > 10000;

-- GROUP BY with ROLLUP (totals per group + grand total)
SELECT role, COUNT(*) AS count
FROM users
GROUP BY role WITH ROLLUP;
```

---

## 4. JOINs

### Visual Reference

```
INNER JOIN              LEFT JOIN               RIGHT JOIN
   A ∩ B               A (all) + B (match)    A (match) + B (all)
  [A [AB] B]          [AAAB  B]               [A  ABBB]

FULL OUTER JOIN         SELF JOIN               CROSS JOIN
A ∪ B (all)            Table joined to itself   Every combo (A × B)
```

### JOIN Examples

```sql
-- INNER JOIN — only matching rows in both tables
SELECT u.username, o.order_id, o.order_date, p.name AS product
FROM orders o
INNER JOIN users    u ON o.user_id    = u.user_id
INNER JOIN products p ON o.product_id = p.product_id;

-- LEFT JOIN — all users, even those with no orders
SELECT u.username, COUNT(o.order_id) AS order_count
FROM users u
LEFT JOIN orders o ON u.user_id = o.user_id
GROUP BY u.username
ORDER BY order_count DESC;

-- Find users who have NEVER placed an order
SELECT u.username
FROM users u
LEFT JOIN orders o ON u.user_id = o.user_id
WHERE o.order_id IS NULL;

-- RIGHT JOIN — all orders, even if user deleted
SELECT o.order_id, u.username
FROM users u
RIGHT JOIN orders o ON u.user_id = o.user_id;

-- FULL OUTER JOIN (MySQL uses UNION workaround)
SELECT u.username, o.order_id
FROM users u LEFT JOIN orders o ON u.user_id = o.user_id
UNION
SELECT u.username, o.order_id
FROM users u RIGHT JOIN orders o ON u.user_id = o.user_id;

-- SELF JOIN — manager/employee hierarchy
SELECT e.username AS employee, m.username AS manager
FROM users e
JOIN users m ON e.manager_id = m.user_id;

-- CROSS JOIN — every combination (use carefully!)
SELECT u.username, p.name
FROM users u
CROSS JOIN products p;     -- rows = users × products
```

---

## 5. Subqueries & CTEs

### Subqueries

```sql
-- In WHERE clause
SELECT * FROM products
WHERE price > (SELECT AVG(price) FROM products);

-- Find users who placed orders for products > $100
SELECT DISTINCT u.username
FROM users u
WHERE u.user_id IN (
    SELECT o.user_id FROM orders o
    JOIN products p ON o.product_id = p.product_id
    WHERE p.price > 100
);

-- Correlated subquery — references outer query
SELECT u.username, u.role,
    (SELECT COUNT(*) FROM orders o WHERE o.user_id = u.user_id) AS order_count
FROM users u;

-- EXISTS — more efficient than IN for large datasets
SELECT u.username
FROM users u
WHERE EXISTS (
    SELECT 1 FROM orders o WHERE o.user_id = u.user_id
);

-- NOT EXISTS
SELECT u.username
FROM users u
WHERE NOT EXISTS (
    SELECT 1 FROM orders o WHERE o.user_id = u.user_id
);
```

### CTEs (Common Table Expressions) — WITH clause

```sql
-- Simple CTE — more readable than nested subquery
WITH active_users AS (
    SELECT user_id, username FROM users WHERE status = TRUE
),
user_orders AS (
    SELECT user_id, COUNT(*) AS order_count
    FROM orders GROUP BY user_id
)
SELECT u.username, COALESCE(uo.order_count, 0) AS orders
FROM active_users u
LEFT JOIN user_orders uo ON u.user_id = uo.user_id
ORDER BY orders DESC;

-- Recursive CTE — for hierarchical data
WITH RECURSIVE org_hierarchy AS (
    -- Base case: top-level employees (no manager)
    SELECT user_id, username, manager_id, 0 AS level
    FROM users
    WHERE manager_id IS NULL

    UNION ALL

    -- Recursive case: employees with managers
    SELECT u.user_id, u.username, u.manager_id, oh.level + 1
    FROM users u
    INNER JOIN org_hierarchy oh ON u.manager_id = oh.user_id
)
SELECT * FROM org_hierarchy ORDER BY level, username;
```

---

## 6. Data Manipulation (INSERT, UPDATE, DELETE)

```sql
-- INSERT single row
INSERT INTO users (username, email, role)
VALUES ('test_user', 'test@example.com', 'customer');

-- INSERT multiple rows
INSERT INTO products (name, category, price, stock) VALUES
    ('Chrome Browser', 'Software', 0.00, 999),
    ('Firefox Browser', 'Software', 0.00, 999),
    ('MacBook Pro', 'Electronics', 2499.99, 50);

-- INSERT from SELECT
INSERT INTO archived_orders
SELECT * FROM orders WHERE order_date < '2023-01-01';

-- UPDATE
UPDATE users SET role = 'admin' WHERE username = 'standard_user';

-- UPDATE multiple columns
UPDATE products
SET price = price * 0.9, stock = stock + 100
WHERE category = 'Electronics';

-- UPDATE with JOIN (MySQL)
UPDATE orders o
JOIN products p ON o.product_id = p.product_id
SET o.total_price = o.quantity * p.price;

-- DELETE
DELETE FROM users WHERE status = FALSE;

-- DELETE with JOIN
DELETE o FROM orders o
JOIN users u ON o.user_id = u.user_id
WHERE u.username = 'test_user';

-- TRUNCATE — delete all rows, reset AUTO_INCREMENT (faster than DELETE *)
TRUNCATE TABLE test_data;

-- UPSERT — INSERT or UPDATE if exists
INSERT INTO users (username, email)
VALUES ('admin', 'admin@example.com')
ON DUPLICATE KEY UPDATE email = VALUES(email);     -- MySQL

INSERT INTO users (username, email)
VALUES ('admin', 'admin@example.com')
ON CONFLICT (username) DO UPDATE SET email = EXCLUDED.email;  -- PostgreSQL
```

---

## 7. DDL — CREATE, ALTER, DROP

```sql
-- CREATE TABLE
CREATE TABLE test_results (
    id         INT PRIMARY KEY AUTO_INCREMENT,
    test_name  VARCHAR(200) NOT NULL,
    status     ENUM('PASS','FAIL','SKIP') NOT NULL,
    duration   INT COMMENT 'Duration in milliseconds',
    run_date   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    browser    VARCHAR(50),
    error_msg  TEXT
);

-- ALTER TABLE
ALTER TABLE test_results ADD COLUMN environment VARCHAR(50);
ALTER TABLE test_results DROP COLUMN error_msg;
ALTER TABLE test_results MODIFY COLUMN test_name VARCHAR(500) NOT NULL;
ALTER TABLE test_results RENAME COLUMN run_date TO executed_at;
ALTER TABLE test_results ADD INDEX idx_status (status);

-- DROP
DROP TABLE IF EXISTS test_results;
DROP DATABASE IF EXISTS test_db;

-- CREATE INDEX
CREATE INDEX idx_user_email ON users(email);
CREATE UNIQUE INDEX idx_unique_username ON users(username);
CREATE INDEX idx_order_date ON orders(order_date, user_id);  -- composite
DROP INDEX idx_user_email ON users;

-- CREATE VIEW
CREATE VIEW active_user_orders AS
SELECT u.username, u.email, COUNT(o.order_id) AS total_orders
FROM users u
LEFT JOIN orders o ON u.user_id = o.user_id
WHERE u.status = TRUE
GROUP BY u.user_id, u.username, u.email;

SELECT * FROM active_user_orders WHERE total_orders > 3;
```

---

## 8. Indexes

Indexes speed up `SELECT` but slow down `INSERT`/`UPDATE`/`DELETE`.

```sql
-- When to use index:
-- ✅ Columns in WHERE clause
-- ✅ Columns in JOIN ON clause
-- ✅ Columns in ORDER BY
-- ✅ Columns in GROUP BY
-- ❌ Columns rarely used in queries
-- ❌ Small tables (full scan faster)
-- ❌ Columns with very low cardinality (e.g., boolean)

-- Types
CREATE INDEX        idx_name ON table(col);          -- non-unique
CREATE UNIQUE INDEX idx_name ON table(col);          -- unique (like PK)
CREATE INDEX        idx_comp ON table(col1, col2);   -- composite
CREATE FULLTEXT INDEX idx_ft ON products(name, description);  -- full-text search

-- EXPLAIN — see how MySQL executes a query (check if index is used)
EXPLAIN SELECT * FROM orders WHERE user_id = 5;
-- Look for: type=ref (index used), type=ALL (full scan — bad for large tables)
```

---

## 9. Constraints

```sql
-- PRIMARY KEY — unique + not null
user_id INT PRIMARY KEY

-- FOREIGN KEY — referential integrity
FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE CASCADE    -- auto-delete child when parent deleted
    ON UPDATE CASCADE    -- auto-update child FK when parent PK changes

-- UNIQUE
email VARCHAR(100) UNIQUE

-- NOT NULL
username VARCHAR(50) NOT NULL

-- DEFAULT
status BOOLEAN DEFAULT TRUE
role VARCHAR(20) DEFAULT 'customer'

-- CHECK (MySQL 8.0+)
price DECIMAL(10,2) CHECK (price >= 0)
quantity INT CHECK (quantity > 0)

-- ADD constraint after table creation
ALTER TABLE orders ADD CONSTRAINT chk_quantity CHECK (quantity > 0);
ALTER TABLE users ADD CONSTRAINT uq_email UNIQUE (email);
```

---

## 10. Stored Procedures & Functions

```sql
-- Stored Procedure — can have IN/OUT/INOUT params, no return value
DELIMITER $$
CREATE PROCEDURE GetUserOrders(IN p_username VARCHAR(50))
BEGIN
    SELECT o.order_id, p.name, o.quantity, o.total_price, o.order_date
    FROM orders o
    JOIN users    u ON o.user_id    = u.user_id
    JOIN products p ON o.product_id = p.product_id
    WHERE u.username = p_username
    ORDER BY o.order_date DESC;
END $$
DELIMITER ;

-- Call procedure
CALL GetUserOrders('standard_user');

-- Function — returns a value
DELIMITER $$
CREATE FUNCTION GetOrderCount(p_user_id INT)
RETURNS INT DETERMINISTIC
BEGIN
    DECLARE v_count INT;
    SELECT COUNT(*) INTO v_count FROM orders WHERE user_id = p_user_id;
    RETURN v_count;
END $$
DELIMITER ;

-- Use function in query
SELECT username, GetOrderCount(user_id) AS orders FROM users;
```

---

## 11. Transactions & ACID

### ACID Properties

| Property | Meaning |
|---------|---------|
| **Atomicity** | All operations succeed or ALL are rolled back |
| **Consistency** | Database moves from one valid state to another |
| **Isolation** | Concurrent transactions don't interfere |
| **Durability** | Committed data persists (survives crashes) |

```sql
-- Transaction example
START TRANSACTION;

    -- Debit from sender
    UPDATE accounts SET balance = balance - 500 WHERE user_id = 1;

    -- Credit to receiver
    UPDATE accounts SET balance = balance + 500 WHERE user_id = 2;

    -- Check for negative balance
    IF (SELECT balance FROM accounts WHERE user_id = 1) < 0 THEN
        ROLLBACK;                  -- undo all changes
    ELSE
        COMMIT;                    -- make changes permanent
    END IF;

-- SAVEPOINT — partial rollback
START TRANSACTION;
    INSERT INTO orders ...;
    SAVEPOINT after_order;
    UPDATE products SET stock = stock - 1 ...;
    ROLLBACK TO SAVEPOINT after_order;  -- undo stock update, keep order insert
COMMIT;
```

### Isolation Levels

| Level | Dirty Read | Non-Repeatable Read | Phantom Read |
|-------|-----------|---------------------|-------------|
| READ UNCOMMITTED | ✅ possible | ✅ possible | ✅ possible |
| READ COMMITTED | ❌ prevented | ✅ possible | ✅ possible |
| REPEATABLE READ | ❌ prevented | ❌ prevented | ✅ possible (default MySQL) |
| SERIALIZABLE | ❌ prevented | ❌ prevented | ❌ prevented |

```sql
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
```

---

## 12. Window Functions

Window functions operate over a "window" of rows without collapsing them into groups.

```sql
-- ROW_NUMBER — unique sequential number within partition
SELECT username, order_date, total_price,
    ROW_NUMBER() OVER (PARTITION BY user_id ORDER BY order_date DESC) AS rn
FROM orders o JOIN users u ON o.user_id = u.user_id;

-- Get latest order per user
SELECT * FROM (
    SELECT *, ROW_NUMBER() OVER (PARTITION BY user_id ORDER BY order_date DESC) AS rn
    FROM orders
) ranked
WHERE rn = 1;

-- RANK / DENSE_RANK
SELECT name, price,
    RANK() OVER (ORDER BY price DESC) AS rank,         -- gaps on ties (1,2,2,4)
    DENSE_RANK() OVER (ORDER BY price DESC) AS d_rank  -- no gaps (1,2,2,3)
FROM products;

-- SUM / AVG / COUNT as window functions (running totals)
SELECT order_id, order_date, total_price,
    SUM(total_price) OVER (PARTITION BY user_id ORDER BY order_date) AS running_total,
    AVG(total_price) OVER (PARTITION BY user_id) AS user_avg
FROM orders;

-- LAG / LEAD — access previous/next row value
SELECT order_date, total_price,
    LAG(total_price) OVER (ORDER BY order_date) AS prev_order_amount,
    LEAD(total_price) OVER (ORDER BY order_date) AS next_order_amount
FROM orders;

-- NTILE — divide into equal buckets
SELECT username, total_price,
    NTILE(4) OVER (ORDER BY total_price) AS quartile
FROM orders JOIN users ON orders.user_id = users.user_id;
```

---

## 13. Database Testing Concepts

### Types of DB Testing

| Type | What to Verify |
|------|---------------|
| **Functional** | CRUD operations produce correct results |
| **Data integrity** | FK constraints, uniqueness, NOT NULL enforced |
| **Schema validation** | Tables, columns, types, constraints exist as designed |
| **Stored procedure testing** | SPs produce correct output |
| **Transaction testing** | Rollback works, concurrent transactions isolated |
| **Performance** | Query execution time, index usage |
| **Security** | No SQL injection, proper access control |
| **Migration testing** | Data preserved correctly after schema migration |

### Common DB Test Queries for SDET

```sql
-- 1. Verify test data was inserted
SELECT COUNT(*) FROM users WHERE username = 'test_user_1234';

-- 2. Verify no duplicate records created
SELECT username, COUNT(*) AS cnt FROM users GROUP BY username HAVING cnt > 1;

-- 3. Verify order total is computed correctly
SELECT o.order_id,
       o.total_price AS stored,
       o.quantity * p.price AS computed,
       o.total_price = o.quantity * p.price AS is_correct
FROM orders o JOIN products p ON o.product_id = p.product_id;

-- 4. Check FK integrity
SELECT * FROM orders WHERE user_id NOT IN (SELECT user_id FROM users);
SELECT * FROM orders WHERE product_id NOT IN (SELECT product_id FROM products);

-- 5. Find orphaned records after delete
SELECT o.* FROM orders o LEFT JOIN users u ON o.user_id = u.user_id WHERE u.user_id IS NULL;

-- 6. Verify audit trail
SELECT * FROM audit_log WHERE table_name = 'users' AND operation = 'INSERT'
ORDER BY changed_at DESC LIMIT 10;

-- 7. Data type validation
SELECT * FROM products WHERE price != ROUND(price, 2);  -- more than 2 decimal places

-- 8. Check for test data pollution
SELECT COUNT(*) FROM users WHERE username LIKE 'test_%';
```

---

## 14. JDBC in Java

```java
import java.sql.*;

// 1. Load driver (Java 6+ auto-loads)
// 2. Get connection
String url  = "jdbc:mysql://localhost:3306/saucedb";
String user = "root";
String pass = "password";

try (Connection conn = DriverManager.getConnection(url, user, pass)) {

    // 3. Create Statement
    Statement stmt = conn.createStatement();
    ResultSet rs   = stmt.executeQuery("SELECT * FROM users WHERE role = 'admin'");

    while (rs.next()) {
        int    id       = rs.getInt("user_id");
        String username = rs.getString("username");
        String email    = rs.getString("email");
        System.out.println(id + " | " + username + " | " + email);
    }

    // 4. PreparedStatement — prevents SQL injection
    String sql = "SELECT * FROM users WHERE username = ? AND status = ?";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, "standard_user");
    ps.setBoolean(2, true);
    ResultSet rs2 = ps.executeQuery();

    // 5. INSERT/UPDATE/DELETE
    String insertSql = "INSERT INTO test_results (test_name, status) VALUES (?, ?)";
    PreparedStatement insert = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
    insert.setString(1, "Login Test");
    insert.setString(2, "PASS");
    int rows = insert.executeUpdate();
    ResultSet keys = insert.getGeneratedKeys();
    if (keys.next()) System.out.println("New ID: " + keys.getInt(1));

    // 6. Transaction
    conn.setAutoCommit(false);
    try {
        ps1.executeUpdate();
        ps2.executeUpdate();
        conn.commit();
    } catch (SQLException e) {
        conn.rollback();
        throw e;
    } finally {
        conn.setAutoCommit(true);
    }

} catch (SQLException e) {
    e.printStackTrace();
}
```

### DB Utility Class for Test Framework

```java
public class DatabaseUtils {
    private static Connection connection;
    private static final String URL  = ConfigManager.getInstance().get("db.url");
    private static final String USER = ConfigManager.getInstance().get("db.user");
    private static final String PASS = ConfigManager.getInstance().get("db.password");

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASS);
        }
        return connection;
    }

    public static List<Map<String, Object>> executeQuery(String sql, Object... params) throws SQLException {
        List<Map<String, Object>> results = new ArrayList<>();
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) ps.setObject(i + 1, params[i]);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();
            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 1; i <= colCount; i++)
                    row.put(meta.getColumnName(i), rs.getObject(i));
                results.add(row);
            }
        }
        return results;
    }

    public static int executeUpdate(String sql, Object... params) throws SQLException {
        try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) ps.setObject(i + 1, params[i]);
            return ps.executeUpdate();
        }
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) connection.close();
    }
}

// In tests:
List<Map<String, Object>> rows = DatabaseUtils.executeQuery(
    "SELECT * FROM orders WHERE user_id = ?", userId);
Assert.assertEquals(rows.size(), 3, "User should have 3 orders");
Assert.assertEquals(rows.get(0).get("status"), "COMPLETED");
```

---

## 15. Interview Q&A

**Q: What is the difference between `DELETE`, `TRUNCATE`, and `DROP`?**
> `DELETE` — removes rows matching WHERE, row-by-row, can be rolled back, triggers fire, keeps structure.
> `TRUNCATE` — removes ALL rows, faster (deallocates pages), resets AUTO_INCREMENT, can't be rolled back (DDL), no triggers.
> `DROP` — removes the entire table (structure + data), can't be rolled back.

**Q: What is the difference between INNER JOIN and LEFT JOIN?**
> INNER JOIN returns only rows where there is a match in BOTH tables. LEFT JOIN returns ALL rows from the left table, and matching rows from the right — unmatched right rows show NULL.

**Q: What is a primary key vs unique key?**
> Primary key: unique + NOT NULL + only ONE per table + creates clustered index.
> Unique key: allows ONE NULL (only one because NULL ≠ NULL), multiple per table, creates non-clustered index.

**Q: What is normalization? What are the normal forms?**
> Normalization removes data redundancy and improves data integrity.
> **1NF**: Atomic values, no repeating groups, each row unique.
> **2NF**: 1NF + no partial dependency (non-key columns depend on whole primary key).
> **3NF**: 2NF + no transitive dependency (non-key columns don't depend on other non-key columns).
> **BCNF**: Every determinant is a candidate key.

**Q: What is the difference between `HAVING` and `WHERE`?**
> `WHERE` filters rows BEFORE aggregation — can't use aggregate functions.
> `HAVING` filters groups AFTER aggregation — used with `GROUP BY`, can use aggregate functions like `COUNT()`, `SUM()`.

**Q: What is a deadlock in a database?**
> Two transactions each hold a lock the other needs — both wait forever. DB engine detects this and kills one transaction (the "victim") with an error. Prevention: always acquire locks in the same order, keep transactions short.

**Q: What is an index? When should you not use one?**
> An index is a data structure (B-Tree by default) that speeds up lookups at the cost of write performance and storage. Don't index: small tables, low-cardinality columns (boolean), columns rarely in WHERE/JOIN/ORDER BY, heavily written tables where read performance is secondary.

**Q: Write a query to find the second highest salary.**
```sql
-- Method 1: subquery
SELECT MAX(salary) FROM employees WHERE salary < (SELECT MAX(salary) FROM employees);

-- Method 2: LIMIT + OFFSET
SELECT salary FROM employees ORDER BY salary DESC LIMIT 1 OFFSET 1;

-- Method 3: DENSE_RANK
SELECT salary FROM (
    SELECT salary, DENSE_RANK() OVER (ORDER BY salary DESC) AS rnk FROM employees
) t WHERE rnk = 2;
```

**Q: What is SQL injection? How do you prevent it?**
> SQL injection inserts malicious SQL code through user input:
> Input: `' OR '1'='1` → query becomes: `WHERE username='' OR '1'='1'` → always true, bypasses auth.
> Prevention: **PreparedStatements** (parameterized queries) — user input is treated as data, not code. Never concatenate user input into SQL strings.
