package med.voll.api.domain.appointment;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medic.Speciality;

public record AppointmentDTO(@NotNull Long patientId, Long medicId,
		@NotNull @Future LocalDateTime date, Speciality speciality) {
}
