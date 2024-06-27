package med.voll.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import med.voll.api.domain.appointment.AppointmentDTO;
import med.voll.api.domain.appointment.AppointmentDetailsDTO;
import med.voll.api.domain.appointment.AppointmentService;
import med.voll.api.domain.medic.Speciality;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class AppointmentControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JacksonTester<AppointmentDTO> appointmentDTOTester;

	@Autowired
	private JacksonTester<AppointmentDetailsDTO> appointmentDetailsDTOTester;

	@MockBean
	private AppointmentService appointmentService;

	@Test
	@DisplayName("Should return 400 HTTP status when invalid data is provided.")
	@WithMockUser
	void testScheduleAnAppointment400Error() throws Exception {
		var token = generateJwtToken();
		var response = mockMvc.perform(MockMvcRequestBuilders
				.post("/appointments")
				.header("Authorization", "Bearer " + token))
				.andReturn().getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
	}

	@Test
	@DisplayName("Should return 201 HTTP status when valid data is provided.")
	@WithMockUser
	void testScheduleAnAppointment201Status() throws Exception {
		var token = generateJwtToken();
		var speciality = Speciality.CARDIOLOGY;
		var patientId = 3l;
		var medicId = 2l;
		var date = LocalDateTime.now().plusHours(1);
		var appointmentDetails = new AppointmentDetailsDTO(null, patientId, medicId, date);

		when(appointmentService.schedule(any())).thenReturn(appointmentDetails);

		var response = mockMvc.perform(MockMvcRequestBuilders
				.post("/appointments")
				.contentType(MediaType.APPLICATION_JSON)
				.content(appointmentDTOTester.write(new AppointmentDTO(patientId, medicId, date, speciality)).getJson())
				.header("Authorization", "Bearer " + token))
				.andReturn().getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		var expectedResponse = appointmentDetailsDTOTester
				.write(appointmentDetails).getJson();

		// System.out.println("RESPONSE: " + response.);

		assertEquals(expectedResponse, response.getContentAsString());
	}

	private String generateJwtToken() {
		return "mock-jwt-token";
	}
}
