package med.voll.api.domain.appointment;

import java.time.LocalDateTime;

public record AppointmentDetailsDTO(
		Long id,
		Long patientId,
		Long medicId,
		LocalDateTime date) {
}
