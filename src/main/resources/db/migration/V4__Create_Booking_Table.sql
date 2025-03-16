CREATE TABLE IF NOT EXISTS Booking (
    BookingID SERIAL PRIMARY KEY,
    CustomerID INT NOT NULL,
    RoomID INT NOT NULL,
    BookingDate DATE,
    RoomPrice DECIMAL(10, 2) NOT NULL,
    ExpectedCheckInDate DATE,
    ExpectedCheckOutDate DATE,
    CheckInDate DATE,
    CheckOutDate DATE,
    Status VARCHAR(15) DEFAULT 'Pending',
    FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID) ON DELETE CASCADE,
    FOREIGN KEY (RoomID) REFERENCES Room(RoomID) ON DELETE CASCADE
);