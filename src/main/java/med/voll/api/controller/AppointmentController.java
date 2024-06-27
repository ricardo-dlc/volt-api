package med.voll.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.appointment.AppointmentDTO;
import med.voll.api.domain.appointment.AppointmentDetailsDTO;
import med.voll.api.domain.appointment.AppointmentService;

@RestController
@RequestMapping("/appointments")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {
	@Autowired
	private AppointmentService appointmentService;

	@PostMapping
	public ResponseEntity<AppointmentDetailsDTO> scheduleAnAppointment(
			@RequestBody @Valid AppointmentDTO appointmentDTO, UriComponentsBuilder uriComponentsBuilder) {
		AppointmentDetailsDTO appointmentDetailsDTO = appointmentService.schedule(appointmentDTO);

		URI url = uriComponentsBuilder.path("/appointments/{id}").buildAndExpand(appointmentDetailsDTO.id()).toUri();

		return ResponseEntity.created(url).body(appointmentDetailsDTO);
	}
}
