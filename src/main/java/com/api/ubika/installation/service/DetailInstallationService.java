package com.api.ubika.installation.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.americaloc.api.APIOperationResult;
import com.americaloc.api.GenericLocationInfo;
import com.americaloc.api.admin.AdminAPIController;
import com.api.ubika.account.entity.AccountToken;
import com.api.ubika.account.repository.IAccountTokenRepository;
import com.api.ubika.account.util.TokenUtil;
import com.api.ubika.form.DetailInstallationForm;
import com.api.ubika.installation.entity.DetailInstallation;
import com.api.ubika.installation.entity.Installation;
import com.api.ubika.installation.repository.IDetailInstallationRepository;
import com.api.ubika.installation.repository.IInstallationRepository;

@Service
public class DetailInstallationService implements IDetailInstallationService {
	
	private static final String TOKEN_INVALID = "Token Invalido";
	private static final String INTERNAL_ERROR = "Erro Interno";
	private static final String FALLO = "Fallo";
	private static final String EXITO = "Exito";
	private static final SimpleDateFormat FORMATO_DATE = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	
	@Autowired
	private IInstallationRepository installationRepository;
	
	@Autowired
	private IDetailInstallationRepository detailInstalltionRepository;
	
	@Autowired
	private IAccountTokenRepository tokenRepository;
	
	@Value("${my.property.url}")
	private String url;
	
	@Value("${my.property.user}")
	private String user;
	
	@Value("${my.property.pass}")
	private String pass;

	@Override
	public ResponseEntity<Object> findById(String token, Integer id) {
		try {
			if (validarToken(token)) {
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
			if (validarToken(token)) {
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
			if (validarToken(token)) {
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
			if (validarToken(token)) {
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
	
	private DetailInstallation castFormEntity(DetailInstallationForm form) throws ParseException {
		DetailInstallation entity = new DetailInstallation();
		entity.setId(form.getId());
		entity.setIdInstallation(form.getIdInstallation());
		entity.setIdDevice(form.getIdDevice());
		entity.setTask(form.getTask());
		entity.setStatus(form.getStatus());
		if (form.getDateInit() != null && !form.getDateInit().equals("")) {
			entity.setDateInit(FORMATO_DATE.parse(form.getDateInit()).getTime());
		}		
		entity.setDescription(form.getDescription());
		if (form.getDateFinish() != null && !form.getDateFinish().equals("")) {
			entity.setDateFinish(FORMATO_DATE.parse(form.getDateFinish()).getTime());
		}		
		return entity;
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

	@Override
	public ResponseEntity<Object> findByIdInstallation(String token, Integer idInstallation) {
		try {
			if (validarToken(token)) {
				List<DetailInstallation> details = detailInstalltionRepository.findByIdInstallationAll(idInstallation);
				return new ResponseEntity<Object>(castEntityForm(details), HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(TOKEN_INVALID, HttpStatus.FORBIDDEN);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private List<DetailInstallationForm> castEntityForm(List<DetailInstallation> details) {
		List<DetailInstallationForm> lista = new ArrayList<DetailInstallationForm>();
		details.forEach((detail) -> {
			DetailInstallationForm form = new DetailInstallationForm();
			form.setId(detail.getId());
			form.setIdInstallation(detail.getIdInstallation());
			form.setIdDevice(detail.getIdDevice());
			form.setTask(detail.getTask());
			form.setStatus(detail.getStatus());
			switch(detail.getTask()) {
			case "accuary":
				form.setTask("Posición");
				break;
			case "automaticTracking":
				form.setTask("Coordenada");
				break;
			case "ignitionOff":
				form.setTask("Apagado");
				break;
			case "ignitionOffRemote":
				form.setTask("Apagado Remoto");
				break;
			case "ignitionOn":
				form.setTask("Encendido");
				break;
			case "panicButton":
				form.setTask("Botón de panico");
				break;
			default:
				form.setTask(detail.getTask());
				break;
		}
			if (detail.getDateInit() != null) {
				form.setDateInit(FORMATO_DATE.format(new Date(detail.getDateInit())));
			}			
			form.setDescription(detail.getDescription());
			if (detail.getDateFinish() != null) {
				form.setDateFinish(FORMATO_DATE.format(new Date(detail.getDateFinish())));
			}			
			lista.add(form);
		});
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<Object> runTask(String token, Integer idDetail) {
		try {
			if (validarToken(token)) {
				DetailInstallation detail = detailInstalltionRepository.findById(idDetail).get();
				Installation installation = installationRepository.findById(detail.getIdInstallation()).get();
				AdminAPIController api = new AdminAPIController();
				if (api.setDBParameters(url, user, pass)) {
					//token = "df1c3473-0da7-434b-9e82-5d86d5e21258"; //Token America
					token = "a2877a11-c679-4bb6-b0db-1902cdbea698"; //Token Ubika
					Calendar fecha = Calendar.getInstance();
					long to = fecha.getTimeInMillis();
					//long to = 1653368400000L;
					fecha.add(Calendar.HOUR, -24);
					long from = fecha.getTimeInMillis();
					//long from = 1653454799000L;
					installation.setDateInstallation(new Date().getTime());
					detail.setDateInit(new Date().getTime());
					ArrayList<String> events = new ArrayList<String>();
					events.add(detail.getTask());
					detail.setStatus(0);					
					detail.setDateInit(new Date().getTime());
					APIOperationResult result = api.getGenericLocationsWithinIntervalFilteringByEventTypes(token, detail.getIdDevice(), from, to, events, false);
					List<GenericLocationInfo> lista = (ArrayList<GenericLocationInfo>) result.data;
					if (lista != null) {
						for (GenericLocationInfo info : lista) {
							if (info.eventType.equals(detail.getTask())) {
								detail.setStatus(2);
								detail.setDescription(EXITO);
								installation.setAddresGps("https://www.google.com/maps/search/?api=1&query="+info.latitude+","+info.longitude);
								break;
							}				
						}
					} else {
						detail.setStatus(1);
					}
					
					if (detail.getStatus() == 1) {
						detail.setDescription(FALLO);
					}
					detail.setDateFinish(new Date().getTime());
					detailInstalltionRepository.save(detail);
					List<DetailInstallation> details = detailInstalltionRepository.findByIdInstallationAll(installation.getId());
					int contadorTask = 0;
					for (DetailInstallation detalle : details) {
						if (detalle.getStatus() == 2) {
							contadorTask++;
						}
					}
					installation.setStatus(1);
					if (contadorTask == details.size()) {
						installation.setStatus(2);
						installation.setDescription("Finalizada Exitosamente");
					} else {
						installation.setDescription("Prueba completadas ("+contadorTask+"/"+(details.size())+")");
					}
					List<DetailInstallationForm> detalles = castearDetalles(details);					
					installation.setDateFinish(new Date().getTime());
					installationRepository.save(installation);
					return new ResponseEntity<Object>(detalles, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
				}
			} else {
				return new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private List<DetailInstallationForm> castearDetalles(List<DetailInstallation> detalles) {
		List<DetailInstallationForm> detallesForm = new ArrayList<>();
		for (DetailInstallation detalle : detalles) {
			DetailInstallationForm form = new DetailInstallationForm();
			form.setId(detalle.getId());
			form.setIdDevice(detalle.getIdDevice());
			form.setIdInstallation(detalle.getIdInstallation());
			form.setStatus(detalle.getStatus());
			switch(detalle.getTask()) {
				case "accuary":
					form.setTask("Posición");
					break;
				case "automaticTracking":
					form.setTask("Coordenada");
					break;
				case "ignitionOff":
					form.setTask("Apagado");
					break;
				case "ignitionOffRemote":
					form.setTask("Apagado Remoto");
					break;
				case "ignitionOn":
					form.setTask("Encendido");
					break;
				case "panicButton":
					form.setTask("Botón de panico");
					break;
				default:
					form.setTask(detalle.getTask());
					break;
			}
			if (detalle.getDateInit() != null) {
				Date fecha = new Date(detalle.getDateInit());
				form.setDateInit(FORMATO_DATE.format(fecha));
			}
			form.setDescription(detalle.getDescription());
			if (detalle.getDateFinish() != null) {
				Date fecha = new Date(detalle.getDateFinish());
				form.setDateFinish(FORMATO_DATE.format(fecha));
			}			
			detallesForm.add(form);
		}
		return detallesForm;
	}

	

}
