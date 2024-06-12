package med.volt.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.volt.api.medic.Medic;
import med.volt.api.medic.MedicDTO;
import med.volt.api.medic.MedicRepository;

@RestController
@RequestMapping("medics")
public class MedicController {
	@Autowired
	private MedicRepository medicRepository;

	@PostMapping
	public void registerMedic(@RequestBody @Valid MedicDTO medicDTO) {
		medicRepository.save(new Medic(medicDTO));
	}
}
