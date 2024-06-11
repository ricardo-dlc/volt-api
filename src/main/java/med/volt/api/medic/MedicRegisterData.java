package med.volt.api.medic;

import med.volt.api.address.AddressData;

public record MedicRegisterData(
		String fullName,
		String email,
		String document,
		Specialty speciality,
		AddressData address) {

}
