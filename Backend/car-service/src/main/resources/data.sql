-- Brand table data
INSERT INTO brand (id, name, creation_date, logo_image) VALUES (1, 'Lamborghini', '2020-06-03' , 'lamborghini-logo.jpg');
INSERT INTO brand (id, name, creation_date, logo_image) VALUES (2, 'Ferrari', '2018-10-24', 'ferrari-logo.jpg');
INSERT INTO brand (id, name, creation_date, logo_image) VALUES (3, 'Porsche', '2020-08-12' , 'porsche-logo.jpg');
INSERT INTO brand (id, name, creation_date, logo_image) VALUES (4, 'Aston Martin', '2019-05-16', 'aston-martin-logo.jpg');
INSERT INTO brand (id, name, creation_date, logo_image) VALUES (5, 'McLaren', '2020-11-30', 'mclaren-logo.jpg');
INSERT INTO brand (id, name, creation_date, logo_image) VALUES (6, 'Bugatti', '2021-09-14', 'bugatti-logo.jpg');
INSERT INTO brand (id, name, creation_date, logo_image) VALUES (7, 'Koenigsegg', '2022-04-30', 'koenigsegg-logo.jpg');

-- Model table data
INSERT INTO model (id, name, id_brand, created_at) VALUES (1, 'Huracan', 1, '2022-01-15 10:30:00');
INSERT INTO model (id, name, id_brand, created_at) VALUES (2, 'Aventador', 1, '2021-12-01 08:45:00');
INSERT INTO model (id, name, id_brand, created_at) VALUES (3, '488 GTB', 2, '2021-11-20 15:20:00');
INSERT INTO model (id, name, id_brand, created_at) VALUES (4, 'Portofino', 2, '2021-10-05 12:00:00');
INSERT INTO model (id, name, id_brand, created_at) VALUES (5, '911', 3, '2022-02-28 09:15:00');
INSERT INTO model (id, name, id_brand, created_at) VALUES (6, 'Cayenne', 3, '2022-01-10 11:20:00');
INSERT INTO model (id, name, id_brand, created_at) VALUES (7, 'DB11', 4, '2021-12-25 14:30:00');
INSERT INTO model (id, name, id_brand, created_at) VALUES (8, 'Vantage', 4, '2021-11-18 10:45:00');
INSERT INTO model (id, name, id_brand, created_at) VALUES (9, '720S', 5, '2022-03-05 16:40:00');
INSERT INTO model (id, name, id_brand, created_at) VALUES (10, '570S', 5, '2022-02-15 13:20:00');
INSERT INTO model (id, name, id_brand, created_at) VALUES (11, 'Chiron', 6, '2022-05-20 10:00:00');
INSERT INTO model (id, name, id_brand, created_at) VALUES (12, 'Veyron', 6, '2022-03-12 14:30:00');
INSERT INTO model (id, name, id_brand, created_at) VALUES (13, 'Jesko', 7, '2022-06-05 12:45:00');
INSERT INTO model (id, name, id_brand, created_at) VALUES (14, 'Regera', 7, '2022-07-01 09:30:00');

-- Car table data
INSERT INTO car (id, year, image, id_model) VALUES (1, 2022, 'lamborghini1.jpg', 1); -- 2022 Lamborghini Huracan
INSERT INTO car (id, year, image, id_model) VALUES (2, 2021, 'lamborghini1.jpg', 3); -- 2021 Ferrari 488 GTB
INSERT INTO car (id, year, image, id_model) VALUES (3, 2023, 'ferrari.jpg', 2); -- 2023 Lamborghini Aventador
INSERT INTO car (id, year, image, id_model) VALUES (4, 2022, 'porsche.webp', 5); -- 2022 Porsche 911
INSERT INTO car (id, year, image, id_model) VALUES (5, 2021, 'porsche.webp', 6); -- 2021 Porsche Cayenne
INSERT INTO car (id, year, image, id_model) VALUES (6, 2021, 'aston-martin.webp', 7); -- 2021 Aston Martin DB11
INSERT INTO car (id, year, image, id_model) VALUES (7, 2020, 'aston-martin.webp', 8); -- 2020 Aston Martin Vantage
INSERT INTO car (id, year, image, id_model) VALUES (8, 2022, 'mclaren.jpg', 9); -- 2022 McLaren 720S
INSERT INTO car (id, year, image, id_model) VALUES (9, 2021, 'mclaren.jpg', 10); -- 2021 McLaren 570S
INSERT INTO car (id, year, image, id_model) VALUES (10, 2022, 'bugatti.webp', 11); -- 2022 Bugatti Chiron
INSERT INTO car (id, year, image, id_model) VALUES (11, 2021, 'bugatti.webp', 12); -- 2021 Bugatti Veyron
INSERT INTO car (id, year, image, id_model) VALUES (12, 2022, 'koenigsegg.jpg', 13); -- 2022 Koenigsegg Jesko
INSERT INTO car (id, year, image, id_model) VALUES (13, 2023, 'koenigsegg.jpg', 14); -- 2023 Koenigsegg Regera

-- CarPart table data
INSERT INTO car_part (id, description) VALUES (1, 'Brake pads for Lamborghini Huracan');
INSERT INTO car_part (id, description) VALUES (2, 'Oil filter for Ferrari 488 GTB');
INSERT INTO car_part (id, description) VALUES (3, 'Spark plugs for Lamborghini Aventador');
INSERT INTO car_part (id, description) VALUES (4, 'Air filter for Lamborghini Huracan');
INSERT INTO car_part (id, description) VALUES (5, 'Fuel filter for Ferrari 488 GTB');
INSERT INTO car_part (id, description) VALUES (6, 'Battery for Lamborghini Aventador');

-- CarsParts table data
INSERT INTO cars_parts (car_id, part_id) VALUES (1, 1); -- Brake pads for 2022 Lamborghini Huracan
INSERT INTO cars_parts (car_id, part_id) VALUES (2, 2); -- Oil filter for 2021 Ferrari 488 GTB
INSERT INTO cars_parts (car_id, part_id) VALUES (1, 4); -- Air filter for 2022 Lamborghini Huracan
INSERT INTO cars_parts (car_id, part_id) VALUES (2, 5); -- Fuel filter for 2021 Ferrari 488 GTB
INSERT INTO cars_parts (car_id, part_id) VALUES (3, 3); -- Spark plugs for 2023 Lamborghini Aventador
INSERT INTO cars_parts (car_id, part_id) VALUES (3, 6); -- Battery for 2023 Lamborghini Aventador
