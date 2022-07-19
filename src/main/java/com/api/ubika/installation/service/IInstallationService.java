package com.api.ubika.installation.service;

import org.springframework.http.ResponseEntity;

import com.api.ubika.form.InstallationForm;

public interface IInstallationService {
	
	ResponseEntity<Object> save(String token, InstallationForm form);
	
	ResponseEntity<Object> delete(String token, Integer id);
	
	ResponseEntity<Object> findById(String token, Integer id);
	
	ResponseEntity<Object> findAll(String token);
	
	ResponseEntity<Object> findAllByAccount(String token, int idAccount);
	
	void runTask(int idInstallation);
	
	ResponseEntity<Object> report(String token, String dateInit, String dateFin, int estados);

}
