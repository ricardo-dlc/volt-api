package med.volt.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.volt.api.medic.Medic;
import med.volt.api.medic.MedicCreateDTO;
import med.volt.api.medic.MedicGetDTO;
import med.volt.api.medic.MedicRepository;
import med.volt.api.medic.MedicUpdateDTO;

@RestController
@RequestMapping("/medics")
public class MedicController {
	@Autowired
	private MedicRepository medicRepository;

	@PostMapping
	public void registerMedic(@RequestBody @Valid MedicCreateDTO medicDTO) {
		medicRepository.save(new Medic(medicDTO));
	}

	@GetMapping
	public Page<MedicGetDTO> getMedics(@PageableDefault(size = 5) Pageable page) {
		return medicRepository.findAll(page).map(MedicGetDTO::new);
	}

	@PutMapping
	@Transactional
	public void updateMedic(@RequestBody @Valid MedicUpdateDTO updatePayload) {
		Medic medic = medicRepository.getReferenceById(updatePayload.id());
		medic.update(updatePayload);
	}
}
