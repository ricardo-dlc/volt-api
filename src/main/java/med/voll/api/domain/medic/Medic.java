package med.voll.api.domain.medic;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import med.voll.api.domain.address.Address;

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
	private Boolean active;
	@Enumerated(EnumType.STRING)
	private Speciality speciality;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	@Setter
	private Address address;

	public Medic(MedicCreateDTO medicDTO) {
		this.active = true;
		this.fullName = medicDTO.fullName();
		this.address = new Address(medicDTO.address());
		this.document = medicDTO.document();
		this.email = medicDTO.email();
		this.speciality = medicDTO.speciality();
		this.phone = medicDTO.phone();
	}

	public void update(MedicUpdateDTO updatePayload) {
		if (updatePayload.fullName() != null) {
			this.fullName = updatePayload.fullName();
		}

		if (updatePayload.phone() != null) {
			this.document = updatePayload.phone();
		}

		if (updatePayload.address() != null) {
			this.address = address.update(updatePayload.address());
		}
	}

	public void deactivate() {
		this.active = false;
	}

	@Override
	public String toString() {
		return "Medic [id=" + id + ", fullName=" + fullName + ", email=" + email + ", phone=" + phone + ", document="
				+ document + ", active=" + active + ", speciality=" + speciality + ", address=" + address + "]";
	}

}
