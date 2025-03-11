CREATE TABLE IF NOT EXISTS InventoryTransactions (
    TransactionID SERIAL PRIMARY KEY,
    ProductID INT NOT NULL REFERENCES Product(ProductID),
    BookingID INT REFERENCES Booking(BookingID),
    EmployeeID INT REFERENCES Employee(EmployeeID),
    Quantity INT NOT NULL,
    TransactionType VARCHAR(10) NOT NULL CHECK (TransactionType IN ('Import', 'Export')),
    TransactionDate DATE DEFAULT CURRENT_TIMESTAMP,
    Remarks TEXT
);