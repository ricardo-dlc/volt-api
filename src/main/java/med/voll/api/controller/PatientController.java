package med.voll.api.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.address.Address;
import med.voll.api.domain.address.AddressRepository;
import med.voll.api.domain.patient.Patient;
import med.voll.api.domain.patient.PatientCreateDTO;
import med.voll.api.domain.patient.PatientGetDTO;
import med.voll.api.domain.patient.PatientRepository;
import med.voll.api.domain.patient.PatientUpdateDTO;

@RestController
@RequestMapping("/patients")
public class PatientController {
	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AddressRepository addressRepository;

	@PostMapping
	@Transactional
	public ResponseEntity<PatientGetDTO> registerMedic(@RequestBody @Valid PatientCreateDTO patientCreateDTO,
			UriComponentsBuilder uriComponentsBuilder) {
		Address address = new Address(patientCreateDTO.address());
		addressRepository.save(address);

		Patient patient = new Patient(patientCreateDTO);
		patient.setAddress(address);

		patientRepository.save(patient);

		PatientGetDTO patientGetDTO = new PatientGetDTO(patient);

		URI url = uriComponentsBuilder.path("/patients/{id}").buildAndExpand(patient.getId()).toUri();

		return ResponseEntity.created(url).body(patientGetDTO);
	}

	@GetMapping
	public ResponseEntity<Page<PatientGetDTO>> getPatients(@PageableDefault(size = 5) Pageable page) {
		return ResponseEntity.ok(patientRepository.findByActiveTrue(page).map(PatientGetDTO::new));
	}

	@GetMapping("/{id}")
	public ResponseEntity<PatientGetDTO> getPatientById(@PathVariable Long id) {
		Patient patient = patientRepository.getReferenceById(id);
		PatientGetDTO patientGetDTO = new PatientGetDTO(patient);
		return ResponseEntity.ok(patientGetDTO);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<PatientGetDTO> updateMedic(@PathVariable Long id,
			@RequestBody @Valid PatientUpdateDTO updatePayload) {
		Patient patient = patientRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Patient not found"));
		patient.update(updatePayload);
		return ResponseEntity.ok(new PatientGetDTO(patient));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
		Patient patient = patientRepository.getReferenceById(id);
		patient.deactivate();
		return ResponseEntity.noContent().build();
	}
}
