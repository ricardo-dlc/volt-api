package med.voll.api.domain.appointment;

import java.time.LocalDateTime;

public record AppointmentDetailsDTO(
		Long id,
		Long patientId,
		Long medicId,
		LocalDateTime date) {

	public AppointmentDetailsDTO(Appointment data) {
		this(data.getId(), data.getPatient().getId(), data.getMedic().getId(), data.getDate());
	}
}
