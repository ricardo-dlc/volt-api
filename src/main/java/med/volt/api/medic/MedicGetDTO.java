package med.volt.api.medic;

public record MedicGetDTO(
		String fullName,
		String email,
		String document,
		Speciality speciality) {

	public MedicGetDTO(Medic medic) {
		this(medic.getFullName(), medic.getEmail(), medic.getDocument(), medic.getSpeciality());
	}
}