package med.voll.api.domain.appointment.validations;

import med.voll.api.domain.appointment.AppointmentDTO;

public interface QueryValidator {
	public void validate(AppointmentDTO data);
}
