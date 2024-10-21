-- Insert countries
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('Serbia', 'RS', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('USA', 'US', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('Canada', 'CA', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('UK', 'UK', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('Germany', 'DE', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('France', 'FR', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('Japan', 'JP', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('Australia', 'AU', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('Brazil', 'BR', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('Italy', 'IT', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('India', 'IN', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('China', 'CN', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('South Korea', 'KR', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('Spain', 'ES', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('Mexico', 'MX', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('Argentina', 'AR', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('Netherlands', 'NL', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('South Africa', 'ZA', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('New Zealand', 'NZ', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('Switzerland', 'CH', NOW(), FALSE);
INSERT INTO country (name, abbreviation, created_at, is_deleted) VALUES ('Sweden', 'SE', NOW(), FALSE);

-- Insert cities
INSERT INTO city (name, is_deleted, country_id) VALUES ('Novi Sad', FALSE, 1);
INSERT INTO city (name, is_deleted, country_id) VALUES ('Beograd', FALSE, 1);
INSERT INTO city (name, is_deleted, country_id) VALUES ('New York', FALSE, 2);

-- Insert zips
INSERT INTO zip (zip_number, is_deleted, city_id) VALUES (21000, FALSE, 1);
INSERT INTO zip (zip_number, is_deleted, city_id) VALUES (00501, FALSE, 2);

-- Insert addresses
INSERT INTO address (street, street_number, is_deleted, zip_id, city_id) VALUES ('Omladinskog pokreta', '12C', FALSE, 1, 1);
INSERT INTO address (street, street_number, is_deleted, zip_id, city_id) VALUES ('Bulevar oslobođenja', '123', FALSE, 1, 1);
INSERT INTO address (street, street_number, is_deleted, zip_id, city_id) VALUES ('742 Evergreen Terrace', '10A', FALSE, 2, 2);

-- Insert contacts
INSERT INTO contact (home_phone, mobile_phone, email, is_deleted) VALUES ('022622564', '069624564', 'simic@synechron.com', FALSE);
INSERT INTO contact (home_phone, mobile_phone, email, is_deleted) VALUES ('022650088', '0642705840', 'maslac@synechron.com', FALSE);
INSERT INTO contact (home_phone, mobile_phone, email, is_deleted) VALUES ('022650088', '062622564', 'maslacm@synechron.com', FALSE);
INSERT INTO contact (home_phone, mobile_phone, email, is_deleted) VALUES ('022650283', '065154375', 'markovic@synechron.com', FALSE);
INSERT INTO contact (home_phone, mobile_phone, email, is_deleted) VALUES ('022653028', '065154531', 'milinkovic@synechron.com', FALSE);
INSERT INTO contact (home_phone, mobile_phone, email, is_deleted) VALUES ('022650088', '062622564', 'maslacm@synechron.com', FALSE);

-- Insert person
INSERT INTO person (first_name, last_name, jmbg, birth, is_deleted, gender, maritial_status, contact_id,profile_image) VALUES ('John', 'Doe', '98765432109', '1980-04-15T00:00:00', FALSE, '0', '0', 1,'https://static-00.iconduck.com/assets.00/profile-circle-icon-2048x2048-cqe5466q.png');
INSERT INTO person (first_name, last_name, jmbg, birth, is_deleted, gender, maritial_status, contact_id,profile_image) VALUES ('Jane', 'Smith', '98765432109', '1990-08-20 00:00:00', FALSE, '1', '1', 2,'https://static-00.iconduck.com/assets.00/profile-circle-icon-2048x2048-cqe5466q.png');
INSERT INTO person (first_name, last_name, jmbg, birth, is_deleted, gender, maritial_status, contact_id,profile_image) VALUES ('John', 'Johnson', '98765432109', '1996-04-21 00:00:00', FALSE, '1', '2', 3,'https://static-00.iconduck.com/assets.00/profile-circle-icon-2048x2048-cqe5466q.png');
INSERT INTO person (first_name, last_name, jmbg, birth, is_deleted, gender, maritial_status, contact_id) VALUES ('Marko', 'Markovic', '98765432109', '1996-05-23 00:00:00', FALSE, '1', '2', 4);
INSERT INTO person (first_name, last_name, jmbg, birth, is_deleted, gender, maritial_status, contact_id) VALUES ('Milan', 'Milinovic', '98765432109', '1996-08-22 00:00:00', FALSE, '1', '2', 5);
INSERT INTO person (first_name, last_name, jmbg, birth, is_deleted, gender, maritial_status, contact_id,profile_image) VALUES ('Max', 'Bennett', '98765432109', '1996-04-21 00:00:00', FALSE, '1', '2', 6,'https://static-00.iconduck.com/assets.00/profile-circle-icon-2048x2048-cqe5466q.png');

--Sinehron123!
INSERT INTO user (id, email, password, is_enabled, address_id, user_role, is_active) VALUES (1,'john.doe@synechron.com', '$2a$12$sl04.BXGa7xUrF8nPWJQD.BoXZzahIVFAuRw4.sutZc1Ar.AImK2e', TRUE, 1, 0, TRUE);
INSERT INTO user (id, email, password, is_enabled, address_id, user_role, is_active) VALUES (2,'jane.smith@synechron.com', '$2a$12$sl04.BXGa7xUrF8nPWJQD.BoXZzahIVFAuRw4.sutZc1Ar.AImK2e', TRUE, 2, 2, TRUE);
INSERT INTO user (id, email, password, is_enabled, address_id, user_role, is_active) VALUES (3,'admin.admin@synechron.com', '$2a$12$sl04.BXGa7xUrF8nPWJQD.BoXZzahIVFAuRw4.sutZc1Ar.AImK2e', TRUE, 2, 6, TRUE);
INSERT INTO user (id, email, password, is_enabled, address_id, user_role, is_active) VALUES (4,'max.bennett@synechron.com', '$2a$12$sl04.BXGa7xUrF8nPWJQD.BoXZzahIVFAuRw4.sutZc1Ar.AImK2e', TRUE, 3, 4, TRUE);
--!Hashedpassword4
INSERT INTO user (id, email, password, is_enabled, address_id, user_role, is_active) VALUES (5,'marko.markovic@synechron.com', '$2a$12$GQw0PUaz6qOkLEWoyQVVvuI49wXRlZ7dqi7Z4L6C0Rx6W0igGgWF2', TRUE, 2, 0, TRUE);
--!Hashedpassword5
INSERT INTO user (id, email, password, is_enabled, address_id, user_role, is_active) VALUES (6,'milan.milinovic@synechron.com', '$2a$12$l3Hri5plUrAvnuwSnJTj7OQq5mEYWdKJFMIJt4zeRx0IkE7XJc7WK', TRUE, 2, 0, TRUE);

-- Populate the tasks
INSERT INTO task (name, status, assign_date, user_id, is_deleted) VALUES ('Respond to Customer Inquiries', 0, NOW(), 1, FALSE);
INSERT INTO task (name, status, assign_date, user_id, is_deleted) VALUES ('Market Research', 1, NOW(), 2, FALSE);

-- Insert drivers
INSERT INTO driver (id, licence_number, licence_obtained, years_insured) VALUES (2, 'license123', '2021-01-01', 5);

-- Insert risks
INSERT INTO risk (description, is_deleted) VALUES ('Drivers residing in areas with high accident rates are exposed to greater risk', FALSE);

-- Populate the join table for driver_risks
INSERT INTO driver_risks (driver_id, risk_id) VALUES (2, 1);

--Insert Subscriber role
INSERT INTO subscriber_role (name, is_deleted) VALUES ('Basic', FALSE);

-- Insert Subscriber
INSERT INTO subscriber (id, role_id) VALUES (1, 1);
INSERT INTO subscriber (id, role_id) VALUES (4, 1);
INSERT INTO subscriber (id, role_id) VALUES (5, 1);