package med.voll.api.medic;

import jakarta.validation.constraints.NotNull;
import med.voll.api.address.AddressDTO;

public record MedicUpdateDTO(
		@NotNull Long id,
		String fullName,
		String document,
		AddressDTO address) {
}
