CREATE TABLE IF NOT EXISTS Product (
    ProductID SERIAL PRIMARY KEY,
    ProductName VARCHAR(100) NOT NULL,
    UnitPrice DECIMAL(10, 2) NOT NULL,
    Description TEXT,
    Unit VARCHAR(50) NOT NULL
);