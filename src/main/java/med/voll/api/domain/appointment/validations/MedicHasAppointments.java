package med.voll.api.domain.appointment.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.AppointmentDTO;
import med.voll.api.domain.appointment.AppointmentRepository;

@Component
public class MedicHasAppointments implements QueryValidator {
	@Autowired
	private AppointmentRepository repository;

	public void validate(AppointmentDTO data) {
		if (data.medicId() == null) {
			return;
		}
		var medicHasAppointment = repository.existsByMedicIdAndDate(data.medicId(), data.date());

		if (medicHasAppointment) {
			throw new ValidationException("This doctor already has an appointment scheduled at this time.");
		}
	}
}
