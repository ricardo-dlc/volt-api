CREATE TABLE patients (
    id BIGINT NOT NULL AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    document VARCHAR(6) NOT NULL UNIQUE,
    address_id BIGINT,
    active TINYINT DEFAULT 1,
    PRIMARY KEY (id),
    CONSTRAINT fk_patient_address FOREIGN KEY (address_id) REFERENCES address (id)
)
