--liquibase formatted sql

--changeset author:1
CREATE TABLE address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    house_number VARCHAR(20),
    street VARCHAR(255),
    city VARCHAR(100),
    country VARCHAR(100),
    post_code VARCHAR(20)
);

CREATE TABLE amenity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE
);

CREATE TABLE hotel (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description CLOB,
    brand VARCHAR(100),
    address_id BIGINT,
    phone VARCHAR(50),
    email VARCHAR(100),
    check_in VARCHAR(10),
    check_out VARCHAR(10),
    FOREIGN KEY (address_id) REFERENCES address(id)
);

CREATE TABLE hotel_amenities (
    hotel_id BIGINT,
    amenity_id BIGINT,
    PRIMARY KEY (hotel_id, amenity_id),
    FOREIGN KEY (hotel_id) REFERENCES hotel(id),
    FOREIGN KEY (amenity_id) REFERENCES amenity(id)
);