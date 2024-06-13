package med.volt.api.controller;

import jakarta.validation.constraints.NotNull;
import med.volt.api.address.AddressDTO;

public record MedicUpdateDTO(
		@NotNull Long id,
		String fullName,
		String document,
		AddressDTO address) {
}
