CREATE TABLE IF NOT EXISTS BookingUsage (
    BookingUsageID SERIAL PRIMARY KEY,
    BookingID INT NOT NULL,
    ServiceID INT,
    ProductID INT,
    ServiceUsagePrice INT,
    Quantity INT DEFAULT 1 NOT NULL,
    UsageDate DATE NOT NULL,
    FOREIGN KEY (BookingID) REFERENCES Booking(BookingID),
    FOREIGN KEY (ServiceID) REFERENCES Service(ServiceID),
    FOREIGN KEY (ProductID) REFERENCES Product(ProductID)
);