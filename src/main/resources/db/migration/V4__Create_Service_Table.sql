CREATE TABLE IF NOT EXISTS Service (
    ServiceID SERIAL PRIMARY KEY,
    ServiceName VARCHAR(100) NOT NULL,
    ServicePrice DECIMAL(10, 2) NOT NULL,
    ServiceType VARCHAR(100) NOT NULL,
    Description TEXT
);