-- Example migration script using Flyway
-- This script moves address-related data from medic table to address table
-- Flyway will automatically handle rollback if any part of this script fails

-- Start of migration script (implicit start of transaction by Flyway)

-- Create address table if not exists
CREATE TABLE IF NOT EXISTS address (
    id BIGINT NOT NULL AUTO_INCREMENT,
    street VARCHAR(100) NOT NULL,
    district VARCHAR(100) NOT NULL,
    complement VARCHAR(100),
    number VARCHAR(20),
    city VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE medics
ADD COLUMN address_id BIGINT;

-- Move data from medic table to address table
INSERT INTO address (street, district, complement, number, city)
SELECT street, district, complement, number, city
FROM medics;

-- Update the medic table to set address_id
UPDATE medics m
JOIN address a ON m.street = a.street
AND m.district = a.district
AND m.complement = a.complement
AND m.number = a.number
AND m.city = a.city
SET m.address_id = a.id;

-- Drop columns from medic table
ALTER TABLE medics
DROP COLUMN street,
DROP COLUMN district,
DROP COLUMN complement,
DROP COLUMN number,
DROP COLUMN city;

ALTER TABLE medics
ADD CONSTRAINT fk_medic_address
    FOREIGN KEY (address_id)
    REFERENCES address(id);

-- End of migration script (implicit commit by Flyway)

-- Flyway will automatically handle rollback if any statement above fails