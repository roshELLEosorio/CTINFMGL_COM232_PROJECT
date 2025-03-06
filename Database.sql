CREATE DATABASE airbnb;
USE airbnb;

CREATE TABLE tbl_users (
	userId INT PRIMARY KEY AUTO_INCREMENT,
    phoneNumber BIGINT(11) NOT NULL,
    username VARCHAR (50) NOT NULL,
    password VARCHAR (100) NOT NULL,
    birthdate DATE NOT NULL,
    role ENUM("User", "Host") NOT NULL DEFAULT "User"
);

CREATE TABLE tbl_booking (
	bookingId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    propertyId INT NOT NULL,
    userId INT NOT NULL,
    checkOutDate DATE NOT NULL,
    checkInDate DATE NOT NULL
);

CREATE TABLE tbl_property (
	propertyId INT PRIMARY KEY AUTO_INCREMENT,
    userId INT NOT NULL,
    propertyName VARCHAR(100) NOT NULL,
    propertyAddress VARCHAR(255) NOT NULL,
    description TEXT NULL,
    rating DECIMAL(2, 1) DEFAULT 0
);

CREATE TABLE tbl_review(
	reviewId INT PRIMARY KEY AUTO_INCREMENT,
    propertyId INT NOT NULL,
    bookingId INT NOT NULL,
    userId INT NOT NULL,
	date DATE NOT NULL,
    rating DECIMAL(2, 1)
);

CREATE TABLE tbl_payment (
	paymentId INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    bookingId INT NOT NULL,
    paymentStatus ENUM("Paid", "Unpaid") NOT NULL DEFAULT "Unpaid",
    paymentMethod VARCHAR(50) NULL DEFAULT null,
    currency VARCHAR(50) NOT NULL DEFAULT "PHP"
);


