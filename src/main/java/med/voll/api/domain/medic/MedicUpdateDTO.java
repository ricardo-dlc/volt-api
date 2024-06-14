package med.voll.api.domain.medic;

import med.voll.api.domain.address.AddressDTO;

public record MedicUpdateDTO(
		String fullName,
		String document,
		AddressDTO address) {
}
