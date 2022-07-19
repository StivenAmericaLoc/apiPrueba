package com.api.ubika.installation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ubika.installation.service.IDetailInstallationService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/detail/")
public class DetailController {
	
	@Autowired
	private IDetailInstallationService detailService;
	
	@GetMapping("all/{idInstallation}")
	public ResponseEntity<Object> findByIdInstallation(@RequestHeader("Authorization") String token, @PathVariable Integer idInstallation) {
		return detailService.findByIdInstallation(token, idInstallation);
	}
	
	@GetMapping("runTask/{idDetail}")
	public ResponseEntity<Object> runTask(@RequestHeader("Authorization") String token, @PathVariable Integer idDetail) {
		return detailService.runTask(token, idDetail);
	}

}
