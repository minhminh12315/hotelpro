CREATE TABLE IF NOT EXISTS Customer (
    CustomerID SERIAL PRIMARY KEY,
    FullName VARCHAR(100) NOT NULL,
    PhoneNumber VARCHAR(15) NOT NULL,
    Email VARCHAR(100),
    Address TEXT,
    ID_Passport VARCHAR(20) NOT NULL,
    DateOfBirth DATE,
    Gender VARCHAR(10)
);