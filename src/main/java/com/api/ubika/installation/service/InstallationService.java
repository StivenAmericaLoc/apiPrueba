package com.api.ubika.installation.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ubika.account.entity.AccountToken;
import com.api.ubika.account.repository.IAccountTokenRepository;
import com.api.ubika.account.util.TokenUtil;
import com.api.ubika.form.DetailInstallationForm;
import com.api.ubika.form.InstallationForm;
import com.api.ubika.installation.entity.DetailInstallation;
import com.api.ubika.installation.entity.Installation;
import com.api.ubika.installation.repository.IDetailInstallationRepository;
import com.api.ubika.installation.repository.IInstallationRepository;

@Service
public class InstallationService implements IInstallationService {
	
	private static final String TOKEN_INVALID = "Token Invalid";
	private static final String INTERNAL_ERROR = "Internal Error";
	private static final SimpleDateFormat FORMATO_DATE = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	
	@Autowired
	private IInstallationRepository installationRepository;
	
	@Autowired
	private IDetailInstallationRepository detailInstallationRepository;
	
	@Autowired
	private IAccountTokenRepository tokenRepository;

	@Override
	public ResponseEntity<Object> save(String token, InstallationForm form) {
		try {
			if (validarToken(token)) {
				Installation installation = castFormEntity(form);
				int contador = (int) installationRepository.count();
				installation.setId(contador+1);
				installation = installationRepository.save(installation);
				List<DetailInstallation> details = castListFormEntity(form);
				for(DetailInstallation detail : details) {					
					detail.setIdInstallation(installation.getId());
					detail.setDateInit(installation.getDateInstallation());
					int contadorDetail =  (int) detailInstallationRepository.count();
					detail.setId(contadorDetail+1);
					detail = detailInstallationRepository.save(detail);
				}
				form = castEntityForm(installation);
				form.setDetails(castDetailEntityForm(details));
				return new ResponseEntity<Object>(form, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(TOKEN_INVALID, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(INTERNAL_ERROR,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> delete(String token, Integer id) {
		try {
			if (validarToken(token)) {
				List<DetailInstallation> details = detailInstallationRepository.findByIdInstallationAll(id);
				Installation installation = installationRepository.findById(id).get();
				for(DetailInstallation detail : details) {
					detailInstallationRepository.delete(detail);
				}
				installationRepository.delete(installation);
				return new ResponseEntity<Object>(installation, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(TOKEN_INVALID, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(INTERNAL_ERROR,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> findById(String token, Integer id) {
		try {
			if (validarToken(token)) {
				Installation installation = installationRepository.findById(id).get();
				List<DetailInstallation> details = detailInstallationRepository.findByIdInstallationAll(id);
				InstallationForm form = castEntityForm(installation);
				form.setDetails(castDetailEntityForm(details));
				return new ResponseEntity<Object>(form, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(TOKEN_INVALID, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(INTERNAL_ERROR,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> findAll(String token) {
		try {
			if (validarToken(token)) {
				List<Installation> installations = new ArrayList<Installation>();
				installationRepository.findAll().forEach(installations::add);
				List<InstallationForm> forms = new ArrayList<InstallationForm>();
				if (installations.size() > 0) {
					for(Installation install : installations) {
						InstallationForm form = castEntityForm(install);
						List<DetailInstallation> details = detailInstallationRepository.findByIdInstallationAll(form.getId());
						form.setDetails(castDetailEntityForm(details));
						forms.add(form);
					}
				}			
				return new ResponseEntity<Object>(forms, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(TOKEN_INVALID, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(INTERNAL_ERROR,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private Installation castFormEntity(InstallationForm form) {
		Installation entity = new Installation();
		entity.setId(form.getId());
		entity.setIdAccount(form.getIdAccount());
		entity.setAddress(form.getAddress());
		entity.setDescription(form.getDescription());
		if (form.getDateInstallation() != null) {
			entity.setDateInstallation(form.getDateInstallation());
		} else {
			Date fecha = new Date();
			entity.setDateInstallation(FORMATO_DATE.format(fecha));
		}
		return entity;
	}
	
	private List<DetailInstallation> castListFormEntity(InstallationForm form) {
		List<DetailInstallation> details = new ArrayList<DetailInstallation>();
		for (DetailInstallationForm detail : form.getDetails()) {
			DetailInstallation entity = new DetailInstallation();
			entity.setId(detail.getId());
			entity.setIdDevice(detail.getIdDevice());
			entity.setTaskCoordenate(detail.getTaskCoordenate());
			entity.setTaskAccuary(detail.getTaskAccuary());
			entity.setTaskOff(detail.getTaskOff());
			entity.setTaskOffRemote(detail.getTaskOffRemote());
			entity.setTaskOn(detail.getTaskOn());
			entity.setTaskPanicBoton(detail.getTaskPanicBoton());
			details.add(entity);
		}
		return details;
	}
	
	private InstallationForm castEntityForm(Installation installation) {
		InstallationForm form = new InstallationForm();
		form.setId(installation.getId());
		form.setIdAccount(installation.getIdAccount());
		form.setAddress(installation.getAddress());
		form.setDescription(installation.getDescription());
		form.setDateInstallation(installation.getDateInstallation());
		form.setDateFinish(installation.getDateFinish());
		return form;
	}
	
	private List<DetailInstallationForm> castDetailEntityForm(List<DetailInstallation> details) {
		List<DetailInstallationForm> listForm =  new ArrayList<DetailInstallationForm>();
		for(DetailInstallation detail : details) {
			DetailInstallationForm form = new DetailInstallationForm();
			form.setId(detail.getId());
			form.setIdDevice(detail.getIdDevice());
			form.setIdInstallation(detail.getIdInstallation());
			form.setTaskCoordenate(detail.getTaskCoordenate());
			form.setTaskAccuary(detail.getTaskAccuary());
			form.setTaskOff(detail.getTaskOff());
			form.setTaskOffRemote(detail.getTaskOffRemote());
			form.setTaskOn(detail.getTaskOn());
			form.setTaskPanicBoton(detail.getTaskPanicBoton());
			form.setDateInit(detail.getDateInit());
			form.setDateFinish(detail.getDateFinish());
			listForm.add(form);
		}
		return listForm;
	}
	
	private boolean validarToken(String token) {
		try {
			TokenUtil util = new TokenUtil();
			AccountToken info = tokenRepository.findByToken(token);
			return util.validarFecha(info.getDateExpired());
		} catch (Exception e) {
			return false;
		}
	}

}
