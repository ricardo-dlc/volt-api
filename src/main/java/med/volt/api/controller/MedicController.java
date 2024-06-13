package med.volt.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.volt.api.medic.Medic;
import med.volt.api.medic.MedicCreateDTO;
import med.volt.api.medic.MedicGetDTO;
import med.volt.api.medic.MedicRepository;

@RestController
@RequestMapping("medics")
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
}
