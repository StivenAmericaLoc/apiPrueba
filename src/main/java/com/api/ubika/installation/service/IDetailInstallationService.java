package com.api.ubika.installation.service;

import org.springframework.http.ResponseEntity;

import com.api.ubika.form.DetailInstallationForm;

public interface IDetailInstallationService {
	
	ResponseEntity<Object> findById(String token, Integer id);
	
	ResponseEntity<Object> findAll(String token);
	
	ResponseEntity<Object> save(String token, DetailInstallationForm form);
	
	ResponseEntity<Object> delete(String token, Integer id);
	
	ResponseEntity<Object> findByIdInstallation(String token, Integer idInstallation);
	
	ResponseEntity<Object> runTask(String token, Integer idDetail);
	

}
