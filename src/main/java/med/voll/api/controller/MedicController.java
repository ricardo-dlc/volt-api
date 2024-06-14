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

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medic.Medic;
import med.voll.api.medic.MedicCreateDTO;
import med.voll.api.medic.MedicGetDTO;
import med.voll.api.medic.MedicRepository;
import med.voll.api.medic.MedicUpdateDTO;

@RestController
@RequestMapping("/medics")
public class MedicController {
	@Autowired
	private MedicRepository medicRepository;

	@PostMapping
	public ResponseEntity<MedicGetDTO> registerMedic(@RequestBody @Valid MedicCreateDTO medicDTO,
			UriComponentsBuilder uriComponentsBuilder) {
		Medic medic = medicRepository.save(new Medic(medicDTO));
		MedicGetDTO medicGetDTO = new MedicGetDTO(medic);

		URI url = uriComponentsBuilder.path("/medics/{id}").buildAndExpand(medic.getId()).toUri();

		return ResponseEntity.created(url).body(medicGetDTO);
	}

	@GetMapping
	public Page<MedicGetDTO> getMedics(@PageableDefault(size = 5) Pageable page) {
		// return medicRepository.findAll(page).map(MedicGetDTO::new);
		return medicRepository.findByActiveTrue(page).map(MedicGetDTO::new);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<MedicGetDTO> updateMedic(@PathVariable Long id,
			@RequestBody @Valid MedicUpdateDTO updatePayload) {
		Medic medic = medicRepository.getReferenceById(id);
		medic.update(updatePayload);
		return ResponseEntity.ok(new MedicGetDTO(medic));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<Void> deleteMedic(@PathVariable Long id) {
		Medic medic = medicRepository.getReferenceById(id);
		medic.deactivate();
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<MedicGetDTO> getMedicById(@PathVariable Long id) {
		Medic medic = medicRepository.getReferenceById(id);
		MedicGetDTO medicGetDTO = new MedicGetDTO(medic);
		return ResponseEntity.ok(medicGetDTO);
	}
}
