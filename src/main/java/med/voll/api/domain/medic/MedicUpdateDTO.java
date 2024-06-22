package med.voll.api.domain.medic;

import jakarta.validation.Valid;
import med.voll.api.domain.address.AddressDTO;

public record MedicUpdateDTO(
		String fullName,
		String phone,
		@Valid AddressDTO address) {
}
