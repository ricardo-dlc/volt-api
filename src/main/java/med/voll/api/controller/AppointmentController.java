package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.domain.appointment.AppointmentDTO;
import med.voll.api.domain.appointment.AppointmentDetailsDTO;
import med.voll.api.domain.appointment.AppointmentService;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
	@Autowired
	private AppointmentService appointmentService;

	@PostMapping
	public ResponseEntity<AppointmentDetailsDTO> scheduleAnAppointment(
			@RequestBody @Valid AppointmentDTO appointmentDTO) {
		System.out.println(appointmentDTO);
		appointmentService.schedule(appointmentDTO);
		return ResponseEntity.ok(new AppointmentDetailsDTO(null, null, null, null));
	}
}
