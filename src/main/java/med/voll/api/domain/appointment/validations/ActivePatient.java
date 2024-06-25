package med.voll.api.domain.appointment.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.AppointmentDTO;
import med.voll.api.domain.patient.PatientRepository;

@Component
public class ActivePatient implements QueryValidator {
	@Autowired
	PatientRepository patientRepository;

	public void validate(AppointmentDTO data) {
		if (data.patientId() == null) {
			return;
		}

		boolean isActivePatient = patientRepository.existsByIdAndActiveTrue(data.patientId());

		if (!isActivePatient) {
			throw new ValidationException("Patient must be active to schedule an appointment.");
		}
	}
}
