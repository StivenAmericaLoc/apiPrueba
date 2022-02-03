package com.api.ubika.installation.service;

import org.springframework.http.ResponseEntity;

import com.api.ubika.form.InstallationForm;

public interface IInstallationService {
	
	ResponseEntity<Object> save(String token, InstallationForm form);
	
	ResponseEntity<Object> delete(String token, Integer id);
	
	ResponseEntity<Object> findById(String token, Integer id);
	
	ResponseEntity<Object> findAll(String token);

}
