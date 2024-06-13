ALTER TABLE medics ADD active TINYINT;

UPDATE medics SET active = 1;
