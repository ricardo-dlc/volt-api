package med.voll.api.medic;

import med.voll.api.address.AddressDTO;

public record MedicUpdateDTO(
		String fullName,
		String document,
		AddressDTO address) {
}
