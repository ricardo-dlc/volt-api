package med.voll.api.domain.patient;

import jakarta.persistence.Entity;
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

@Table(name = "patients")
@Entity(name = "Patient")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String fullName;
	private String email;
	private String phone;
	private String document;
	private Boolean active;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "address_id")
	@Setter
	private Address address;

	public Patient(PatientCreateDTO patientCreateDTO) {
		this.active = true;
		this.fullName = patientCreateDTO.fullName();
		this.address = new Address(patientCreateDTO.address());
		this.document = patientCreateDTO.document();
		this.email = patientCreateDTO.email();
		this.phone = patientCreateDTO.phone();
	}

	public void update(PatientUpdateDTO updatePayload) {
		if (updatePayload.fullName() != null) {
			this.fullName = updatePayload.fullName();
		}

		if (updatePayload.phone() != null) {
			this.phone = updatePayload.phone();
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
		return "Patient [id=" + id + ", fullName=" + fullName + ", email=" + email + ", phone=" + phone + ", document="
				+ document + ", active=" + active + ", address=" + address + "]";
	}

}
