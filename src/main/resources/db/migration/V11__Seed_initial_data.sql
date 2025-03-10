-- Seed data for Employee table V1
INSERT INTO Employee (FullName, PhoneNumber, Email, Role, Password, StartDate)
VALUES
('admin', '1231231234', 'alicej@example.com', 'Manager', '123456789', '2020-01-01'),
('Bob Wilson', '2342342345', 'bobw@example.com', 'Staff', 'hashed_password2', '2021-06-15'),
('Emma Brown', '3453453456', 'emmab@example.com', 'Staff', 'hashed_password3', '2022-05-20'),
('Michael Green', '4564564567', 'michaelg@example.com', 'Manager', 'hashed_password4', '2019-11-10'),
('Sophia Taylor', '5675675678', 'sophiat@example.com', 'Staff', 'hashed_password5', '2023-01-01'),
('James Davis', '6786786789', 'jamesd@example.com', 'Manager', 'hashed_password6', '2018-05-15'),
('Olivia Harris', '7897897890', 'oliviah@example.com', 'Staff', 'hashed_password7', '2021-03-20'),
('Liam Martin', '8908908901', 'liamm@example.com', 'Staff', 'hashed_password8', '2022-08-10'),
('Charlotte Lewis', '9019019012', 'charlottel@example.com', 'Manager', 'hashed_password9', '2017-09-25'),
('Elijah Walker', '0120120123', 'elijahw@example.com', 'Staff', 'hashed_password10', '2020-11-30');

-- Seed data for Customer table V2
INSERT INTO Customer (FullName, PhoneNumber, Email, Address, ID_Passport, DateOfBirth, Gender)
VALUES
('John Doe', '1234567890', 'johndoe@example.com', '123 Main St', 'A123456789', '1990-01-01', 'Male'),
('Jane Smith', '0987654321', 'janesmith@example.com', '456 Elm St', 'B987654321', '1985-06-15', 'Female'),
('Chris Lee', '5556667777', 'chrislee@example.com', '789 Oak St', 'C456789123', '1992-03-10', 'Other'),
('Nancy White', '6667778888', 'nancyw@example.com', '987 Pine St', 'D789123456', '1980-08-22', 'Female'),
('Tom Black', '7778889999', 'tomblack@example.com', '654 Cedar St', 'E321654987', '1975-12-12', 'Male'),
('Emma Brown', '8889990000', 'emmabrown@example.com', '321 Maple St', 'F654321098', '1993-07-15', 'Female'),
('Oliver Green', '9990001111', 'olivergreen@example.com', '567 Birch St', 'G987654321', '1988-05-10', 'Male'),
('Sophia Davis', '1112223333', 'sophiadavis@example.com', '789 Spruce St', 'H123456789', '1991-04-22', 'Female'),
('Liam White', '2223334444', 'liamwhite@example.com', '135 Willow St', 'I456789123', '1982-09-30', 'Male'),
('Isabella Blue', '3334445555', 'isabellablue@example.com', '246 Poplar St', 'J789123456', '1987-12-25', 'Female');

-- Seed data for Room table with 5 floors, 5 rooms each
INSERT INTO Room (RoomType, RoomNumber, Price, Status, Capacity)
VALUES
-- Floor 1
('Single', 101, 100.00, 'Available', 1),
('Double', 102, 150.00, 'Occupied', 2),
('Suite', 103, 300.00, 'Maintenance', 4),
('Single', 104, 120.00, 'Available', 1),
('Double', 105, 160.00, 'Occupied', 2),

-- Floor 2
('Single', 201, 100.00, 'Available', 1),
('Double', 202, 150.00, 'Occupied', 2),
('Suite', 203, 300.00, 'Maintenance', 4),
('Single', 204, 120.00, 'Available', 1),
('Double', 205, 160.00, 'Occupied', 2),

-- Floor 3
('Single', 301, 100.00, 'Available', 1),
('Double', 302, 150.00, 'Occupied', 2),
('Suite', 303, 300.00, 'Maintenance', 4),
('Single', 304, 120.00, 'Available', 1),
('Double', 305, 160.00, 'Occupied', 2),

-- Floor 4
('Single', 401, 100.00, 'Available', 1),
('Double', 402, 150.00, 'Occupied', 2),
('Suite', 403, 300.00, 'Maintenance', 4),
('Single', 404, 120.00, 'Available', 1),
('Double', 405, 160.00, 'Occupied', 2),

-- Floor 5
('Single', 501, 100.00, 'Available', 1),
('Double', 502, 150.00, 'Occupied', 2),
('Suite', 503, 300.00, 'Maintenance', 4),
('Single', 504, 120.00, 'Available', 1),
('Double', 505, 160.00, 'Occupied', 2);

-- Seed data for Booking table V4
INSERT INTO Booking (CustomerID, RoomID, BookingDate, RoomPrice, ExpectedCheckInDate, ExpectedCheckOutDate, CheckInDate, CheckOutDate, Status)
VALUES
(1, 2, '2025-03-01', 150.00, '2025-03-05', '2025-03-10', NULL, NULL, 'Pending'),
(2, 1, '2025-02-25', 100.00, '2025-03-01', '2025-03-02', '2025-03-01', '2025-03-02', 'CheckedOut'),
(3, 3, '2025-02-20', 300.00, '2025-02-22', '2025-02-24', '2025-02-22', NULL, 'CheckedIn'),
(4, 4, '2025-03-02', 120.00, '2025-03-03', '2025-03-05', NULL, NULL, 'Pending'),
(5, 5, '2025-02-28', 160.00, '2025-03-02', '2025-03-04', '2025-03-02', '2025-03-04', 'CheckedOut'),
(6, 6, '2025-03-03', 350.00, '2025-03-06', '2025-03-09', NULL, NULL, 'Pending'),
(7, 7, '2025-03-04', 110.00, '2025-03-08', '2025-03-12', NULL, NULL, 'Pending'),
(8, 8, '2025-03-05', 170.00, '2025-03-09', '2025-03-13', NULL, NULL, 'Pending'),
(9, 9, '2025-03-06', 400.00, '2025-03-10', '2025-03-14', NULL, NULL, 'Pending'),
(10, 10, '2025-03-07', 105.00, '2025-03-11', '2025-03-15', NULL, NULL, 'Pending');

-- Seed data for Product table V5
INSERT INTO Product (ProductName, UnitPrice, Description, Unit)
VALUES
('Bottle of Water', 1.50, '500ml bottle of mineral water', 'bottle'),
('Snack Pack', 2.00, 'Assorted snacks pack', 'pack'),
('Soft Drink', 2.50, '330ml can of soda', 'can'),
('Shampoo', 3.50, 'Travel-size bottle of shampoo', 'bottle'),
('Toothbrush', 1.20, 'Single-use toothbrush', 'piece'),
('Soap Bar', 0.80, 'Small bar of soap', 'piece'),
('Coffee Pack', 5.00, 'Instant coffee pack', 'pack'),
('Tea Pack', 4.50, 'Assorted tea bags', 'pack'),
('Chocolate', 3.00, 'Bar of chocolate', 'bar'),
('Energy Drink', 3.50, '250ml can of energy drink', 'can');

-- Seed data for Inventory table V6
INSERT INTO Inventory (ProductID, Quantity, LastUpdated)
VALUES
(1, 100, CURRENT_TIMESTAMP),
(2, 50, CURRENT_TIMESTAMP),
(3, 200, CURRENT_TIMESTAMP),
(4, 30, CURRENT_TIMESTAMP),
(5, 75, CURRENT_TIMESTAMP),
(6, 120, CURRENT_TIMESTAMP),
(7, 40, CURRENT_TIMESTAMP),
(8, 60, CURRENT_TIMESTAMP),
(9, 90, CURRENT_TIMESTAMP),
(10, 45, CURRENT_TIMESTAMP);

-- Seed data for InventoryTransactions table V7
INSERT INTO InventoryTransactions (ProductID, BookingID, EmployeeID, Quantity, TransactionType, TransactionDate, Remarks)
VALUES
(1, 1, 1, 2, 'Export', '2025-03-05', 'Guest purchase'),
(2, 2, 2, 1, 'Export', '2025-03-02', 'Guest purchase'),
(3, 3, 3, 3, 'Export', '2025-02-22', 'Guest purchase'),
(4, 4, 4, 1, 'Export', '2025-03-03', 'Guest purchase'),
(5, 5, 5, 2, 'Export', '2025-03-04', 'Guest purchase'),
(6, 6, 6, 4, 'Export', '2025-03-06', 'Guest purchase'),
(7, 7, 7, 1, 'Export', '2025-03-08', 'Guest purchase'),
(8, 8, 8, 2, 'Export', '2025-03-09', 'Guest purchase'),
(9, 9, 9, 3, 'Export', '2025-03-10', 'Guest purchase'),
(10, 10, 10, 1, 'Export', '2025-03-11', 'Guest purchase'),
(1, NULL, 1, 50, 'Import', '2025-03-12', 'Restock'),
(2, NULL, 2, 30, 'Import', '2025-03-13', 'Restock'),
(3, NULL, 3, 100, 'Import', '2025-03-14', 'Restock'),
(4, NULL, 4, 20, 'Import', '2025-03-15', 'Restock'),
(5, NULL, 5, 75, 'Import', '2025-03-16', 'Restock'),
(6, NULL, 6, 40, 'Import', '2025-03-17', 'Restock'),
(7, NULL, 7, 60, 'Import', '2025-03-18', 'Restock'),
(8, NULL, 8, 90, 'Import', '2025-03-19', 'Restock'),
(9, NULL, 9, 45, 'Import', '2025-03-20', 'Restock'),
(10, NULL, 10, 50, 'Import', '2025-03-21', 'Restock');


-- Seed data for Service table V8
INSERT INTO Service (ServiceName, ServicePrice, ServiceType, Description)
VALUES
('Room Cleaning', 50.00, 'Housekeeping', 'Daily room cleaning service'),
('Laundry', 30.00, 'Housekeeping', 'Laundry service for clothes'),
('Spa', 100.00, 'Wellness', 'Full body spa treatment'),
('Gym Access', 20.00, 'Fitness', 'Access to the hotel gym'),
('Breakfast', 15.00, 'Dining', 'Buffet breakfast'),
('Airport Shuttle', 40.00, 'Transport', 'Shuttle service to and from the airport'),
('WiFi', 10.00, 'Amenities', 'High-speed internet access'),
('Parking', 25.00, 'Amenities', 'Secure parking space'),
('Mini Bar', 50.00, 'Dining', 'Access to the mini bar in the room'),
('Conference Room', 200.00, 'Business', 'Rental of conference room for meetings');

-- Seed data for BookingUsage table V9
INSERT INTO BookingUsage (BookingID, ServiceID, ProductID, ServiceUsagePrice, Quantity, UsageDate)
VALUES
(1, 1, NULL, 50, 1, '2025-03-05'), -- Room Cleaning service for BookingID 1
(2, 2, NULL, 30, 1, '2025-03-02'), -- Laundry service for BookingID 2
(3, 3, NULL, 100, 1, '2025-02-22'), -- Spa service for BookingID 3
(4, 4, NULL, 20, 1, '2025-03-03'), -- Gym Access service for BookingID 4
(5, 5, NULL, 15, 1, '2025-03-04'), -- Breakfast service for BookingID 5
(6, NULL, 1, 1.50, 2, '2025-03-06'), -- 2 Bottles of Water for BookingID 6
(7, NULL, 2, 2.00, 1, '2025-03-08'), -- 1 Snack Pack for BookingID 7
(8, NULL, 3, 2.50, 3, '2025-03-09'), -- 3 Soft Drinks for BookingID 8
(9, NULL, 4, 3.50, 1, '2025-03-10'), -- 1 Shampoo for BookingID 9
(10, NULL, 5, 1.20, 1, '2025-03-11'); -- 1 Toothbrush for BookingID 10


-- Seed data for Invoice table V10
INSERT INTO Invoice (BookingID, IssueDate, TotalAmount, PaymentMethod, Status)
VALUES
(1, '2025-03-06', 315.00, 'Card', 'Paid'),
(2, '2025-03-02', 110.00, 'Cash', 'Paid'),
(3, '2025-02-23', 350.00, 'EWallet', 'Paid'),
(4, '2025-03-04', 140.00, 'Card', 'Unpaid'),
(5, '2025-03-05', 450.00, 'Cash', 'Paid'),
(6, '2025-03-06', 500.00, 'EWallet', 'Paid'),
(7, '2025-03-07', 270.00, 'Card', 'Unpaid'),
(8, '2025-03-08', 320.00, 'Cash', 'Paid'),
(9, '2025-03-09', 150.00, 'EWallet', 'Paid'),
(10, '2025-03-10', 105.00, 'Card', 'Paid');
