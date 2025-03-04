CREATE TABLE IF NOT EXISTS Room (
    RoomID SERIAL PRIMARY KEY,
    RoomNumber VARCHAR(10) NOT NULL,
    RoomType VARCHAR(50) NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    Status VARCHAR(15) DEFAULT 'Available',
    Capacity INT NOT NULL
);