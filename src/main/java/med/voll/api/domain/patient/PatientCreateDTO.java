package med.voll.api.domain.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.address.AddressDTO;

public record PatientCreateDTO(
		@NotBlank String fullName,
		@NotBlank @Email String email,
		@NotBlank String phone,
		@NotBlank @Pattern(regexp = "\\d{4,6}") String document,
		@NotNull @Valid AddressDTO address) {

}
