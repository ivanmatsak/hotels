--liquibase formatted sql

--changeset author:2

INSERT INTO amenity (id, name) VALUES
(1, 'Free parking'),
(2, 'Free WiFi'),
(3, 'Fitness center'),
(4, 'Swimming pool'),
(5, 'Spa'),
(6, 'Pet-friendly rooms'),
(7, 'On-site restaurant'),
(8, 'Business center'),
(9, 'Casino');

INSERT INTO address (id, house_number, street, city, country, post_code) VALUES
(1, '9', 'Pobediteley Avenue', 'Minsk', 'Belarus', '220004'),
(2, '8', 'Tolstogo Street', 'Minsk', 'Belarus', '220007'),
(3, '1E', 'Dzerzhinsky Avenue', 'Minsk', 'Belarus', '220036'),
(4, '26', 'Marshala Zhukova Street', 'Orenburg', 'Russia', '460000');

INSERT INTO hotel (id, name, description, brand, address_id, phone, email, check_in, check_out) VALUES
(1, 'DoubleTree by Hilton Minsk', 'Luxurious rooms with stunning views from the 20th floor.', 'Hilton', 1,
 '+375 17 309-80-00', 'doubletreeminsk.info@hilton.com', '14:00', '12:00'),

(2, 'Hampton by Hilton Minsk City Centre', 'Modern hotel conveniently located next to the main train station.', 'Hilton', 2,
 '+375 17 215-40-00', 'msqhxhx.hampton@hilton.com', '14:00', '12:00'),

(3, 'Renaissance Minsk Congress Hotel & Spa', 'Upscale hotel with a sophisticated restaurant and extensive spa.', 'Marriott', 3,
 '+375 17 309-90-90', 'info@renaissance.by', '15:00', '12:00'),

(4, 'Hilton Garden Inn Orenburg', 'Centrally located hotel with a 24-hour restaurant and bar.', 'Hilton', 4,
 '+7 353 290-03-00', 'reservation@hgio.ru', '14:00', '12:00');

INSERT INTO hotel_amenities (hotel_id, amenity_id) VALUES (1, 1), (1, 2), (1, 3), (1, 7), (1, 8);
INSERT INTO hotel_amenities (hotel_id, amenity_id) VALUES (2, 1), (2, 2), (2, 3), (2, 8);
INSERT INTO hotel_amenities (hotel_id, amenity_id) VALUES (3, 2), (3, 3), (3, 4), (3, 5), (3, 6), (3, 7), (3, 9);
INSERT INTO hotel_amenities (hotel_id, amenity_id) VALUES (4, 1), (4, 2), (4, 3), (4, 7), (4, 8);