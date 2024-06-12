package med.volt.api.medic;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.volt.api.address.AddressDTO;

public record MedicDTO(
		@NotBlank String fullName,
		@NotBlank @Email String email,
		@NotBlank @Pattern(regexp = "\\d{4,6}") String document,
		@NotNull Specialty speciality,
		@NotNull @Valid AddressDTO address) {

}
