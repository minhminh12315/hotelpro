CREATE TABLE IF NOT EXISTS Employee (
    EmployeeID SERIAL PRIMARY KEY,
    FullName VARCHAR(100) NOT NULL,
    PhoneNumber VARCHAR(15),
    Email VARCHAR(100),
    Role VARCHAR(10) DEFAULT 'Staff',
    Password VARCHAR(255) NOT NULL,
    StartDate DATE NOT NULL
);