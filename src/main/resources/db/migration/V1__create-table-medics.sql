CREATE TABLE medics (
    id BIGINT NOT NULL AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    document VARCHAR(6) NOT NULL UNIQUE,
    speciality VARCHAR(100) NOT NULL,
    street VARCHAR(100) NOT NULL,
    district VARCHAR(100) NOT NULL,
    complement VARCHAR(100),
    number VARCHAR(20),
    city VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
)