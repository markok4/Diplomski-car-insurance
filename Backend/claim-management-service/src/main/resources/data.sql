-- Delete all rows from tables. Order is important because of foreign key constraints

DELETE FROM document;
DELETE FROM appraisal;
DELETE FROM rejection;
DELETE FROM complaint;
DELETE FROM claim_properties;
DELETE FROM accident;
DELETE FROM document_type;

ALTER TABLE document AUTO_INCREMENT = 1;
ALTER TABLE appraisal AUTO_INCREMENT = 1;
ALTER TABLE rejection AUTO_INCREMENT = 1;
ALTER TABLE complaint AUTO_INCREMENT = 1;
ALTER TABLE claim_properties AUTO_INCREMENT = 1;
ALTER TABLE accident AUTO_INCREMENT = 1;
ALTER TABLE document_type AUTO_INCREMENT = 1;



-- Insert data into Accident
INSERT INTO accident (date_of_incident, was_responsible, description, is_deleted) VALUES ('2022-01-01T12:00:00', true, 'Two-car collision at a traffic light', false);
INSERT INTO accident (date_of_incident, was_responsible, description, is_deleted) VALUES ('2022-01-02T15:00:00', false, 'Single-car accident due to icy road conditions', false);

-- Insert data into ClaimProperties
INSERT INTO claim_properties (complaint_time_limit, date_field, date_finished, decision, description, status, is_deleted, accident_id) VALUES ('2023-01-01T00:00:00', '2022-01-03T00:00:00', '2022-01-04T00:00:00', true, 'Claim filed for damages to the front bumper and headlights', 'COMPLAINT_SUBMITTED', false, 1);
INSERT INTO claim_properties (complaint_time_limit, date_field, decision, description, status, is_deleted, accident_id) VALUES ('2023-01-02T00:00:00', '2022-01-05T00:00:00', false, 'Claim filed for damages from sliding into a guardrail', 'CLAIM_UNDER_REVIEW', false, 2);

-- Insert data into DocumentType
INSERT INTO document_type (name, description, file_type, is_deleted) VALUES ('Accident Scene Photo', 'Photos taken at the scene of the accident', 'JPG', false);
INSERT INTO document_type (name, description, file_type, is_deleted) VALUES ('Official Police Report', 'Official report filed by the responding police officers', 'PDF', false);
-- Assuming that IDs are auto-generated and the IDs for the above inserts are 1 and 2

-- Insert data into Document
INSERT INTO document (name, original_name, path, is_deleted, claim_properties_id, document_type_id) VALUES ('DOC_FOTOGRAFIJA_NESRECE_01012022', 'fotografija_nesrece', '/resources/data/images/', false, 1, 1);
INSERT INTO document (name, original_name, path, is_deleted, claim_properties_id, document_type_id) VALUES ('DOC_IZVESTAJ_02012022', 'izvestaj', '/resources/data/documents/', false, 2, 2);

-- Insert data into Appraisal
INSERT INTO appraisal (date_of_appraisal_start, date_of_appraisal_finish, description, estimated_price, is_deleted, claim_properties_id)
VALUES ('2022-01-03T10:00:00', '2022-01-03T15:00:00', 'Initial damage assessment for front bumper and headlights damages', 1200.00, false, 1);

INSERT INTO appraisal (date_of_appraisal_start, date_of_appraisal_finish, description, estimated_price, is_deleted, claim_properties_id)
VALUES ('2022-01-06T09:30:00', '2022-01-06T14:45:00', 'Initial damage assessment for guardrail collision damages', 2300.00, false, 2);

-- Insert data into Rejection
INSERT INTO rejection (date_of_rejection, rejection_reason, description, is_deleted, claim_properties_id) VALUES ('2022-01-06T00:00:00', 'POLICY_NOT_COVERED', 'Claim rejected because the policy does not cover accidents caused by traffic violations', false, 1);
--INSERT INTO rejection (date_of_rejection, rejection_reason, description, is_deleted, claim_properties_id) VALUES ('2022-01-07T00:00:00', 'INCORRECT_DOCUMENTATION', 'Claim rejected due to missing documentation of the accident scene', false, 2);

-- Insert data into Complaint
INSERT INTO complaint (date_of_complaint, description, is_deleted, claim_properties_id) VALUES ('2022-01-08T00:00:00', 'Complaint filed due to claim rejection because of policy coverage', false, 1);
--INSERT INTO complaint (complaint_decision, date_of_complaint, description, is_deleted, claim_properties_id) VALUES ('Decision2', '2022-01-09T00:00:00', 'Complaint Description 2', false, 2);