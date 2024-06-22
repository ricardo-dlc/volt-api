package med.voll.api.domain.address;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "address")
@Entity(name = "Address")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String street;
	private String district;
	private String city;
	private String number;
	private String complement;

	public Address(AddressDTO addressDto) {
		this.street = addressDto.street();
		this.district = addressDto.district();
		this.city = addressDto.city();
		this.number = addressDto.number();
		this.complement = addressDto.complement();
	}

	public Address update(AddressDTO address) {
		this.street = address.street();
		this.district = address.district();
		this.city = address.city();
		this.number = address.number();
		this.complement = address.complement();

		return this;
	}

	@Override
	public String toString() {
		return "Address [street=" + street + ", district=" + district + ", city=" + city + ", number=" + number
				+ ", complement=" + complement + "]";
	}

}
