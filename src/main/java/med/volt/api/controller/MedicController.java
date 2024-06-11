package med.volt.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.volt.api.medic.MedicRegisterData;

@RestController
@RequestMapping("medics")
public class MedicController {
	@PostMapping
	public void registerMedic(@RequestBody MedicRegisterData params) {
		System.out.println(params);
	}
}
