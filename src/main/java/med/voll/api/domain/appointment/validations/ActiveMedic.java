package med.voll.api.domain.appointment.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.AppointmentDTO;
import med.voll.api.domain.medic.MedicRepository;

@Component
public class ActiveMedic implements QueryValidator {
	@Autowired
	private MedicRepository repository;

	public void validate(AppointmentDTO data) {
		if (data.medicId() == null) {
			return;
		}

		boolean isActiveMedic = repository.existsByIdAndActiveTrue(data.medicId());

		if (!isActiveMedic) {
			throw new ValidationException("Medic must be active to schedule an appointment.");
		}
	}
}
