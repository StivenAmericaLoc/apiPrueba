package com.api.ubika.installation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.ubika.form.InstallationForm;
import com.api.ubika.installation.service.IInstallationService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/installation")
public class InstallationController {
	
	@Autowired
	private IInstallationService installationService;
	
	@PostMapping("/save")
	public ResponseEntity<Object> save(@RequestHeader("Authorization") String token, @RequestBody InstallationForm form) {
		return installationService.save(token, form);		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> delete(@RequestHeader("Authorization") String token, @PathVariable Integer id) {
		return installationService.delete(token, id);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@RequestHeader("Authorization") String token, @PathVariable Integer id) {
		return installationService.findById(token, id);
	}
	
	@GetMapping("/all")
	public ResponseEntity<Object> findAll(@RequestHeader("Authorization") String token) {
		return installationService.findAll(token);
	}
	
	@GetMapping("/all/{idUser}")
	public ResponseEntity<Object> findAllByAccount(@RequestHeader("Authorization") String token, @PathVariable Integer idUser) {
		return installationService.findAllByAccount(token, idUser);
	}
	
	@PostMapping("/task/{idInstallation}")
	public void runTask(@RequestHeader("Authorization") String token, @PathVariable Integer idInstallation) {
		installationService.runTask(idInstallation);
	}
	
	@GetMapping("/report")
	public ResponseEntity<Object> findReport(@RequestHeader("Authorization") String token, @RequestParam String dateIni, @RequestParam String dateFin, @RequestParam int estado) {
		return installationService.report(token, dateIni, dateFin, estado);
	}

}
