CREATE TABLE IF NOT EXISTS ServiceUsage (
    ServiceUsageID SERIAL PRIMARY KEY,
    BookingID INT NOT NULL,
    ServiceID INT NOT NULL,
    ServiceUsagePrice INT,
    Quantity INT DEFAULT 1 NOT NULL,
    UsageDate DATE NOT NULL,
    FOREIGN KEY (BookingID) REFERENCES Booking(BookingID),
    FOREIGN KEY (ServiceID) REFERENCES Service(ServiceID)
);