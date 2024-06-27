package med.voll.api.domain.medic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import med.voll.api.domain.address.Address;
import med.voll.api.domain.address.AddressDTO;
import med.voll.api.domain.appointment.Appointment;
import med.voll.api.domain.patient.Patient;
import med.voll.api.domain.patient.PatientCreateDTO;
import net.datafaker.Faker;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MedicRepositoryTest {
	@Autowired
	private MedicRepository repository;

	@Autowired
	private TestEntityManager em;

	private Faker faker = new Faker();

	@Test
	@DisplayName("Should return null if no medic is available at the schedule time.")
	void testGetMedicBySpecialityAndNotInDateScenario1() {
		var nextDate = LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
				.atTime(10, 0);

		var speciality = Speciality.CARDIOLOGY;

		var medic = registerMedic(speciality);
		var patient = registerPatient();

		registerAppointment(medic, patient, nextDate);

		var availableMedic = repository.getMedicBySpecialityAndNotInDate(speciality, nextDate);

		assertNull(availableMedic);
	}

	@Test
	@DisplayName("Should return a medic when a medic is retrieved from the database for an specific time.")
	void testGetMedicBySpecialityAndNotInDateScenario2() {
		var nextDate = LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
				.atTime(10, 0);

		var speciality = Speciality.CARDIOLOGY;

		var medic = registerMedic(speciality);

		var availableMedic = repository.getMedicBySpecialityAndNotInDate(speciality, nextDate);

		assertEquals(medic, availableMedic);
	}

	private Patient registerPatient() {
		var addressPayload = createAddressPayload();
		var address = new Address(addressPayload);
		em.persist(address);

		var patientPayload = createPatientPayload();
		var patient = new Patient(patientPayload);
		patient.setAddress(address);
		em.persist(patient);

		return patient;
	}

	private PatientCreateDTO createPatientPayload() {
		var fullName = faker.name().fullName();
		var email = faker.internet().emailAddress();
		var phoneNumber = faker.numerify("########");
		var document = faker.numerify("######");
		var address = createAddressPayload();
		return new PatientCreateDTO(fullName, email, phoneNumber, document, address);
	}

	private Medic registerMedic(Speciality speciality) {
		var addressPayload = createAddressPayload();
		var address = new Address(addressPayload);
		em.persist(address);

		var medicPayload = createMedicPayload(speciality);
		var medic = new Medic(medicPayload);
		medic.setAddress(address);
		em.persist(medic);

		return medic;
	}

	private void registerAppointment(Medic medic, Patient patient, LocalDateTime date) {
		em.persist(new Appointment(patient, medic, date));
	}

	private MedicCreateDTO createMedicPayload(Speciality speciality) {
		var fullName = faker.name().fullName();
		var email = faker.internet().emailAddress();
		var phoneNumber = faker.numerify("########");
		var document = faker.numerify("######");
		var address = createAddressPayload();
		return new MedicCreateDTO(fullName, email, phoneNumber, document, speciality, address);
	}

	private AddressDTO createAddressPayload() {
		var street = faker.address().streetName();
		var district = faker.address().state();
		var city = faker.address().city();
		var number = faker.address().buildingNumber();
		var complement = faker.address().secondaryAddress();
		return new AddressDTO(street, district, city, number, complement);
	}
}
