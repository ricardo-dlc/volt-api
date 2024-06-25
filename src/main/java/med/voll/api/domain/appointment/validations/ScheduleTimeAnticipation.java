package med.voll.api.domain.appointment.validations;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.AppointmentDTO;

@Component
public class ScheduleTimeAnticipation implements QueryValidator {
	public void validate(AppointmentDTO data) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime appointmentTime = data.date();

		boolean isLessThan30Mins = Duration.between(now, appointmentTime).toMinutes() < 30;

		if (isLessThan30Mins) {
			throw new ValidationException("The appointment must be scheduled at least 30 minutes in advance.");
		}
	}
}
