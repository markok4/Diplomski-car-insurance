-- InsurancePlan
INSERT INTO insurance_plan (id, name, is_premium, is_deleted) VALUES
(1, 'Standard Plan', false, false),
(2, 'Premium Plan', true, false);

-- InsuranceItem
INSERT INTO insurance_item (id, name, is_optional, franchise_percentage, amount, is_deleted) VALUES
(1, 'Comprehensive', false, 20, 500.00, false),
(2, 'Collision', true, 15, 300.00, false),
(3, 'Liability', false, 10, 200.00, false);

-- InsuranceItemInsurancePlan
INSERT INTO insurance_item_insurance_plan (insurance_plan_id, insurance_item_id) VALUES
(1, 1),
(1, 2),
(2, 1),
(2, 3);

-- Proposal
-- Proposal
INSERT INTO proposal (id, is_valid, creation_date, proposal_status, amount, car_plates, is_deleted, insurance_plan_id, car_id, sales_agent_email) VALUES
(1, true, '2022-01-01 12:00:00', 'CONFIRMED', 500.00, 'ABC123', false, 1, 1, 'max.bennett@synechron.com'),
(2, false, '2022-02-01 12:00:00', 'INITIALIZED', 800.00, 'XYZ789', false, 2, 3, NULL),
(3, true, '2022-03-01 12:00:00', 'PAID', 1000.00, 'DEF456', false, 2, 3, 'max.bennett@synechron.com'),
(4, true, '2023-05-01 12:00:00', 'CONFIRMED', 1200.00, 'GHI101', false, 1, 2, NULL),
(5, true, '2023-06-01 12:00:00', 'CONFIRMED', 1300.00, 'JKL202', false, 1, 3, NULL),
(6, true, '2023-11-01 12:00:00', 'CONFIRMED', 1400.00, 'MNO303', false, 2, 2, 'max.bennett@synechron.com'),
(7, true, '2023-12-01 12:00:00', 'CONFIRMED', 1500.00, 'PQR404', false, 2, 3, NULL),
(8, true, '2024-01-01 12:00:00', 'CONFIRMED', 1600.00, 'STU505', false, 1, 1, NULL),
(9, true, '2024-02-01 12:00:00', 'CONFIRMED', 1700.00, 'VWX606', false, 1, 3, 'max.bennett@synechron.com'),
(10, true, '2024-03-01 12:00:00', 'CONFIRMED', 1800.00, 'YZA707', false, 2, 1, NULL);

-- Policy

INSERT INTO policy (id, date_signed, expiring_date, money_received_date, amount, is_deleted, proposal_id, subscriber_id) VALUES
(1, '2022-01-01 12:00:00', '2023-01-01 12:00:00', '2022-01-01 12:00:00', 1000.00, false, 1, 2),
(2, '2022-02-01 12:00:00', '2023-02-01 12:00:00', '2022-02-01 12:00:00', 1500.00, false, 2, 1),
(3, '2023-05-01 12:00:00', '2024-04-30 12:00:00', '2023-05-01 12:00:00', 1200.00, false, 4, 1),
(4, '2023-06-01 12:00:00', '2024-05-01 12:00:00', '2023-06-01 12:00:00', 1300.00, false, 5, 2),
(5, '2023-11-01 12:00:00', '2024-11-01 12:00:00', '2023-11-01 12:00:00', 1400.00, false, 6, 1),
(6, '2023-12-01 12:00:00', '2024-12-01 12:00:00', '2023-12-01 12:00:00', 1500.00, false, 7, 2),
(7, '2024-01-01 12:00:00', '2025-01-01 12:00:00', '2024-01-01 12:00:00', 1600.00, false, 8, 1),
(8, '2024-02-01 12:00:00', '2025-02-01 12:00:00', '2024-02-01 12:00:00', 1700.00, false, 9, 1),
(9, '2024-03-01 12:00:00', '2025-03-01 12:00:00', '2024-03-01 12:00:00', 1800.00, false, 10, 2),
(10, '2024-04-01 12:00:00', '2025-04-01 12:00:00', '2024-04-01 12:00:00', 1900.00, false, 3, 1);


-- Franchise
INSERT INTO franchise (id, percentage, is_deleted, proposal_id, insurance_item_id) VALUES
(1, 10, false, 1, 1),
(2, 15, false, 2, 2),
(3, 8, false, 3, 3);