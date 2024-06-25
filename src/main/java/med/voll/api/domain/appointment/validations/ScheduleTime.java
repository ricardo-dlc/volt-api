package med.voll.api.domain.appointment.validations;

import java.time.DayOfWeek;

import org.springframework.stereotype.Component;

import jakarta.validation.ValidationException;
import med.voll.api.domain.appointment.AppointmentDTO;

@Component
public class ScheduleTime implements QueryValidator {
	public void validate(AppointmentDTO data) {
		boolean isSunday = DayOfWeek.SUNDAY.equals(data.date().getDayOfWeek());
		boolean isBeforeOpen = data.date().getHour() < 7;
		boolean isAfterClose = data.date().getHour() > 19;

		if (isSunday || isBeforeOpen || isAfterClose) {
			throw new ValidationException("Schedule time can be only from Mon to Sat from 07:00 to 19:00.");
		}
	}
}
