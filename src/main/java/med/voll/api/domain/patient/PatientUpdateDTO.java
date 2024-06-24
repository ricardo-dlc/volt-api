package med.voll.api.domain.patient;

import jakarta.validation.Valid;
import med.voll.api.domain.address.AddressDTO;

public record PatientUpdateDTO(
		String fullName,
		String phone,
		@Valid AddressDTO address) {
}
