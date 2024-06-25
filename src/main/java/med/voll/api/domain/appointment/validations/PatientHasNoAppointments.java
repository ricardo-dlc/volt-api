package med.voll.api.domain.appointment.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.AppointmentDTO;
import med.voll.api.domain.appointment.AppointmentRepository;

@Component
public class PatientHasNoAppointments implements QueryValidator {
	@Autowired
	private AppointmentRepository repository;

	public void validate(AppointmentDTO data) {
		var firstHour = data.date().withHour(7);
		var lastHour = data.date().withHour(18);

		var patientHasAppointment = repository.existsByPatientIdAndDateBetween(data.patientId(), firstHour, lastHour);

		if (patientHasAppointment) {
			throw new ValidationException("Patient cannot schedule more than one appointment within the same day.");
		}
	}
}
