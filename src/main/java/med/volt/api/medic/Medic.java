package med.volt.api.medic;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.volt.api.address.Address;

@Table(name = "medics")
@Entity(name = "Medic")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String fullName;
	private String email;
	private String phone;
	private String document;
	@Enumerated(EnumType.STRING)
	private Speciality speciality;
	@Embedded
	private Address address;

	public Medic(MedicCreateDTO medicDTO) {
		this.fullName = medicDTO.fullName();
		this.address = new Address(medicDTO.address());
		this.document = medicDTO.document();
		this.email = medicDTO.email();
		this.speciality = medicDTO.speciality();
		this.phone = medicDTO.phone();
	}
}
