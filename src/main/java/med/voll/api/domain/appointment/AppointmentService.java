package med.voll.api.domain.appointment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.medic.Medic;
import med.voll.api.domain.medic.MedicRepository;
import med.voll.api.domain.patient.Patient;
import med.voll.api.domain.patient.PatientRepository;
import med.voll.api.infra.errors.DataValidationException;
import med.voll.api.domain.appointment.validations.QueryValidator;

@Service
public class AppointmentService {
	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private MedicRepository medicRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private List<QueryValidator> validators;

	public Appointment schedule(AppointmentDTO appointmentDTO) {
		Patient patient = patientRepository.findById(appointmentDTO.patientId())
				.orElseThrow(() -> new DataValidationException("Patient not found"));

		if (appointmentDTO.medicId() != null && !medicRepository.existsById(appointmentDTO.medicId())) {
			System.out.println("Medic not found");
			throw new DataValidationException("Medic not found");
		}

		validators.forEach(v -> v.validate(appointmentDTO));

		Medic medic = chooseMedic(appointmentDTO);
		System.out.println("Choosen Medic is" + medic);
		Appointment appointment = new Appointment(patient, medic, appointmentDTO.date());
		appointmentRepository.save(appointment);

		return appointment;
	}

	private Medic chooseMedic(AppointmentDTO appointmentDTO) {
		if (appointmentDTO.medicId() != null) {
			return medicRepository.getReferenceById(appointmentDTO.medicId());
		}

		if (appointmentDTO.speciality() == null) {
			throw new DataValidationException("Speciality must be provided.");
		}

		return medicRepository.getMedicBySpecialityAndNotInDate(appointmentDTO.speciality(), appointmentDTO.date());
	}
}
