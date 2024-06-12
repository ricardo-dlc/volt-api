package med.volt.api.medic;

import med.volt.api.address.AddressDTO;

public record MedicDTO(
		String fullName,
		String email,
		String document,
		Specialty speciality,
		AddressDTO address) {

}
