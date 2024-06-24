package med.voll.api.domain.patient;

public record PatientGetDTO(
		Long id,
		String fullName,
		String email,
		String document) {

	public PatientGetDTO(Patient patient) {
		this(patient.getId(), patient.getFullName(), patient.getEmail(), patient.getDocument());
	}
}
