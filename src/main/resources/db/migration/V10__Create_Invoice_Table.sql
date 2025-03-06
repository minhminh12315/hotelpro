CREATE TABLE IF NOT EXISTS Invoice (
    InvoiceID SERIAL PRIMARY KEY,
    BookingID INT NOT NULL,
    IssueDate DATE NOT NULL,
    TotalAmount DECIMAL(15, 2) NOT NULL,
    PaymentMethod VARCHAR(10),
    Status VARCHAR(10) DEFAULT 'Unpaid',
    FOREIGN KEY (BookingID) REFERENCES Booking(BookingID)
);