package com.api.ubika.installation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.api.ubika.form.DetailInstallationForm;
import com.api.ubika.installation.entity.DetailInstallation;
import com.api.ubika.installation.repository.IDetailInstallationRepository;

public class DetailInstallationService implements IDetailInstallationService {
	
	private static final String TOKEN_INVALID = "Token Invalid";
	private static final String INTERNAL_ERROR = "Internal Error";
	
	@Autowired
	private IDetailInstallationRepository detailInstalltionRepository;

	@Override
	public ResponseEntity<Object> findById(String token, Integer id) {
		try {
			if (token != null ) {
				DetailInstallation detail = detailInstalltionRepository.findById(id).get();
				return new ResponseEntity<Object>(detail, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(TOKEN_INVALID, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> findAll(String token) {
		try {
			if (token != null ) {
				List<DetailInstallation> details = new ArrayList<DetailInstallation>();
				detailInstalltionRepository.findAll().forEach(details::add);
				return new ResponseEntity<Object>(details, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(TOKEN_INVALID, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> save(String token, DetailInstallationForm form) {
		try {
			if (token != null ) {
				DetailInstallation detail = castFormEntity(form);
				detail = detailInstalltionRepository.save(detail);
				return new ResponseEntity<Object>(detail, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(TOKEN_INVALID, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> delete(String token, Integer id) {
		try {
			if (token != null ) {
				DetailInstallation detail = detailInstalltionRepository.findById(id).get();
				detailInstalltionRepository.delete(detail);
				return new ResponseEntity<Object>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(TOKEN_INVALID, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private DetailInstallation castFormEntity(DetailInstallationForm form) {
		DetailInstallation entity = new DetailInstallation();
		entity.setId(form.getId());
		entity.setIdInstallation(form.getIdInstallation());
		entity.setIdDevice(form.getIdDevice());
		entity.setTaskAccuary(form.getTaskAccuary());
		entity.setTaskCoordenate(form.getTaskCoordenate());
		entity.setTaskOff(form.getTaskOff());
		entity.setTaskOffRemote(form.getTaskOffRemote());
		entity.setTaskOn(form.getTaskOn());
		entity.setTaskPanicBoton(form.getTaskPanicBoton());
		entity.setDateInit(form.getDateInit());
		entity.setDateFinish(form.getDateFinish());
		return entity;
	}

}
