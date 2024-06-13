package med.voll.api.medic;

public record MedicGetDTO(
		Long id,
		String fullName,
		String email,
		String document,
		Speciality speciality) {

	public MedicGetDTO(Medic medic) {
		this(medic.getId(), medic.getFullName(), medic.getEmail(), medic.getDocument(), medic.getSpeciality());
	}
}