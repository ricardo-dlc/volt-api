CREATE TABLE appointments (
    id BIGINT NOT NULL AUTO_INCREMENT,
    medic_id BIGINT NOT NULL,
    patient_id BIGINT NOT NULL,
    date DATETIME NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_appointments_medic_id FOREIGN KEY (medic_id) REFERENCES medics (id),
    CONSTRAINT fk_appointments_patient_id FOREIGN KEY (medic_id) REFERENCES patients (id)
)