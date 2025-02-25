CREATE TABLE IF NOT EXISTS Booking (
    BookingID SERIAL PRIMARY KEY,
    CustomerID INT NOT NULL,
    RoomID INT NOT NULL,
    BookingDate DATE NOT NULL,
    RoomPrice INT,
    CheckInDate DATE NOT NULL,
    CheckOutDate DATE,
    Status VARCHAR(15) DEFAULT 'Pending',
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID),
    FOREIGN KEY (RoomID) REFERENCES Room(RoomID)
);