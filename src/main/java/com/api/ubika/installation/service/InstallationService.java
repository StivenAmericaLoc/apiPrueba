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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.americaloc.api.APIOperationResult;
import com.americaloc.api.GenericLocationInfo;
import com.americaloc.api.admin.AdminAPIController;
import com.api.ubika.account.entity.AccountToken;
import com.api.ubika.account.repository.IAccountTokenRepository;
import com.api.ubika.account.util.TokenUtil;
import com.api.ubika.form.DetailInstallationForm;
import com.api.ubika.form.InstallationForm;
import com.api.ubika.form.ReportForm;
import com.api.ubika.installation.entity.DetailInstallation;
import com.api.ubika.installation.entity.Installation;
import com.api.ubika.installation.repository.IDetailInstallationRepository;
import com.api.ubika.installation.repository.IInstallationRepository;

@Service
public class InstallationService implements IInstallationService {
	
	private static final String TOKEN_INVALID = "Token Invalid";
	private static final String INTERNAL_ERROR = "Internal Error";
	private static final SimpleDateFormat FORMATO_DATE_LARGO = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	private static final SimpleDateFormat FORMATO_DATE_CORTO = new SimpleDateFormat("yyyy-MM-dd");
	private static final int CERO = 0;
	private static final int UNO = 1;
	private static final int DOS = 2;
	private static final String WAITING = "Esperando";
	private static final String FAILED = "Fallido";
	private static final String SUCCESS = "Exitoso";
	
	@Autowired
	private IInstallationRepository installationRepository;
	
	@Autowired
	private IDetailInstallationRepository detailInstallationRepository;
	
	@Autowired
	private IAccountTokenRepository tokenRepository;
	
	@Value("${my.property.url}")
	private String url;
	
	@Value("${my.property.user}")
	private String user;
	
	@Value("${my.property.pass}")
	private String pass;

	@Override
	public ResponseEntity<Object> save(String token, InstallationForm form) {
		try {
			if (validarToken(token)) {
				Installation installation = castFormEntity(form);
				int contador = (int) installationRepository.count();
				installation.setId(contador+1);
				installation.setDescription("Installation pending to start");
				installation.setStatus(0);
				installation = installationRepository.save(installation);
				List<DetailInstallation> details = castListFormEntity(form);
				for(DetailInstallation detail : details) {					
					detail.setIdInstallation(installation.getId());
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
				InstallationForm form = castEntityForm(installation);
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
				//List<Installation> installations = new ArrayList<Installation>();
				//installationRepository.findAll().forEach(installations::add);
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.HOUR, -24);
				List<Installation> installations = installationRepository.findAllByDate(cal.getTimeInMillis());
				List<InstallationForm> forms = new ArrayList<InstallationForm>();
				if (installations.size() > 0) {
					for(Installation install : installations) {
						InstallationForm form = castEntityForm(install);
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
	
	private Installation castFormEntity(InstallationForm form) throws ParseException {
		Installation entity = new Installation();
		entity.setId(form.getId());
		entity.setIdAccount(form.getIdAccount());
		entity.setAddress(form.getAddress());
		entity.setAddresGps("https://www.google.com/maps/search/?api=1&query=0,0");
		entity.setDescription(form.getDescription());
		entity.setNameAccount(form.getNameAccount());
		if (form.getDateInstallation() != null && !form.getDateInstallation().equalsIgnoreCase("")) {
			entity.setDateInstallation(FORMATO_DATE_LARGO.parse(form.getDateInstallation()).getTime());
		} else {
			Date fecha = new Date();
			entity.setDateInstallation(fecha.getTime());
		}
		return entity;
	}
	
	private List<DetailInstallation> castListFormEntity(InstallationForm form) throws ParseException {
		List<DetailInstallation> details = new ArrayList<DetailInstallation>();
		for (DetailInstallationForm detail : form.getDetails()) {
			DetailInstallation entity = new DetailInstallation();
			entity.setId(detail.getId());
			entity.setIdDevice(detail.getIdDevice());
			entity.setTask(detail.getTask());
			entity.setStatus(1);
			if (detail.getDateInit() != null && !detail.getDateInit().equals("")) {
				entity.setDateInit(FORMATO_DATE_LARGO.parse(detail.getDateInit()).getTime());
			}			
			entity.setDescription(detail.getDescription());
			if (detail.getDateFinish() != null && !detail.getDateFinish().equals("")) {
				entity.setDateFinish(FORMATO_DATE_LARGO.parse(detail.getDateFinish()).getTime());
			}
			entity.setDeviceName(detail.getDeviceName());
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
		form.setDateInstallation(FORMATO_DATE_LARGO.format(new Date(installation.getDateInstallation())));
		if (installation.getDateFinish() != null) {
			form.setDateFinish(FORMATO_DATE_LARGO.format(new Date(installation.getDateFinish())));
		}		
		form.setStatus(installation.getStatus());
		return form;
	}
	
	private List<DetailInstallationForm> castDetailEntityForm(List<DetailInstallation> details) {
		List<DetailInstallationForm> listForm =  new ArrayList<DetailInstallationForm>();
		for(DetailInstallation detail : details) {
			DetailInstallationForm form = new DetailInstallationForm();
			form.setId(detail.getId());
			form.setIdDevice(detail.getIdDevice());
			form.setIdInstallation(detail.getIdInstallation());
			form.setTask(detail.getTask());
			form.setStatus(detail.getStatus());
			if (detail.getDateInit() != null) {
				form.setDateInit(FORMATO_DATE_LARGO.format(new Date(detail.getDateInit())));
			}			
			form.setDescription(detail.getDescription());
			if (detail.getDateFinish() != null) {
				form.setDateFinish(FORMATO_DATE_LARGO.format(new Date(detail.getDateFinish())));
			}			
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
	
	@SuppressWarnings("unchecked")
	@Async
	@Override
	public void runTask(int idInstallation) {
		try {
			List<DetailInstallation> details = detailInstallationRepository.findByIdInstallationAll(idInstallation);
			Installation installation = installationRepository.findById(idInstallation).get();
			AdminAPIController api = new AdminAPIController();
			if (api.setDBParameters(url, user, pass)) {
				//String token = "df1c3473-0da7-434b-9e82-5d86d5e21258";//Token americaloc
				String token = "a2877a11-c679-4bb6-b0db-1902cdbea698";//Token ubikanos
				Calendar fecha = Calendar.getInstance();
				long to = fecha.getTimeInMillis();
				//long to = 1653454799000L;
				fecha.add(Calendar.HOUR, -24);
				long from = fecha.getTimeInMillis();
				//long from = 1653368400000L;
				int contadorEventos = details.size();
				int contadorTask = 0;
				installation.setDateInstallation(new Date().getTime());	
				for(DetailInstallation detail : details) {
					try {
						ArrayList<String> events = new ArrayList<String>();
						if (detail.getStatus() == 1) {
							detail.setDateInit(new Date().getTime());
							events.add(detail.getTask());
							detail.setStatus(1);
							detail.setDescription("Fallo");
						} else {
							contadorTask++;
							continue;
						}
						APIOperationResult result = api.getGenericLocationsWithinIntervalFilteringByEventTypes(token, detail.getIdDevice(), from, to, events, false);
						List<GenericLocationInfo> lista = (ArrayList<GenericLocationInfo>) result.data;
						if (lista == null) {
							installation.setStatus(1);
							installation.setDescription("Not events found in ID "+detail.getIdDevice());
							installation.setDateFinish(new Date().getTime());
							installationRepository.save(installation);
							detail.setDateFinish(new Date().getTime());
							detailInstallationRepository.save(detail);
							continue;
						}
						for(GenericLocationInfo info : lista) {
							if (detail.getStatus() == 1 && info.eventType.equalsIgnoreCase(detail.getTask())) {
								detail.setStatus(2);
								detail.setDescription("Exito");
								installation.setAddresGps("http://maps.google.com/maps?q="+info.latitude+","+info.longitude+"&ll="+info.latitude+","+info.longitude+"&z=17");
								contadorTask++;
								break;
							}
						};
						detail.setDateFinish(new Date().getTime());
						detailInstallationRepository.save(detail);
					} catch (Exception e) {
						installation.setStatus(1);
						installation.setDescription("Error interno en la prueba "+ detail.getIdDevice());
						installationRepository.save(installation);
						installation.setDateFinish(new Date().getTime());	
						return;
					}					
				};
				installation.setDateFinish(new Date().getTime()	);		
				if (contadorEventos == contadorTask) {
					installation.setStatus(2);
					installation.setDescription("Finalizada Exitosamente");
				} else {
					installation.setStatus(1);
					installation.setDescription("Pruebas completadas ("+contadorTask+"/"+contadorEventos+")");
				}
			} else {
				installation.setStatus(1);
				installation.setDescription("No hubo conexion a la base de datos");
			}			
			installationRepository.save(installation);
		} catch (Exception e) {
			System.out.println("Hubo un fallo al realizar las pruebas");
		}
		
	}

	@Override
	public ResponseEntity<Object> report(String token, String dateInit, String dateFin, int estado) {
		try {
			if (validarToken(token)) {
				List<Installation> installations = new ArrayList<>();
				if (!dateInit.equals("") && !dateFin.equals("") && estado > 0) {
					Date fechaIni = FORMATO_DATE_CORTO.parse(dateInit);
					Date fechaFin = FORMATO_DATE_CORTO.parse(dateFin);
					Calendar cal = Calendar.getInstance();
					cal.setTime(fechaFin);
					cal.set(Calendar.HOUR, 23);
					cal.set(Calendar.MINUTE, 59);
					cal.set(Calendar.MILLISECOND, 59);
					installations = installationRepository.findAllByDateAndStatus(fechaIni.getTime(), cal.getTimeInMillis(), estado);
				} else if (!dateInit.equals("") && !dateFin.equals("")) {
					Date fechaIni = FORMATO_DATE_CORTO.parse(dateInit);
					Date fechaFin = FORMATO_DATE_CORTO.parse(dateFin);
					Calendar cal = Calendar.getInstance();
					cal.setTime(fechaFin);
					cal.set(Calendar.HOUR, 23);
					cal.set(Calendar.MINUTE, 59);
					cal.set(Calendar.MILLISECOND, 59);
					installations = installationRepository.findAllByDateInstallation(fechaIni.getTime(), cal.getTimeInMillis());
				} else {
					installations = installationRepository.findAllByStatus(estado);
				}
				List<ReportForm> report = castInstallationsReport(installations);
				return new ResponseEntity<Object>(report, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(TOKEN_INVALID, HttpStatus.FORBIDDEN);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private List<ReportForm> castInstallationsReport(List<Installation> installations) {
		List<ReportForm> report = new ArrayList<>();
		for (Installation installation : installations) {			
			List<DetailInstallation> details = detailInstallationRepository.findByIdInstallationAll(installation.getId());
			if (details != null) {
				for (DetailInstallation detail : details) {
					ReportForm data = new ReportForm();
					data.setIdInstallation(installation.getId());
					data.setAccountName(installation.getNameAccount());
					data.setAddress(installation.getAddresGps());
					data.setStatus(getStatusName(installation.getStatus()));
					if (installation.getDateInstallation() != null) {
						Date fecha = new Date(installation.getDateInstallation());
						data.setDateInstallation(FORMATO_DATE_LARGO.format(fecha));
					}					
					data.setIdDetail(detail.getId());
					data.setIdDevice(detail.getIdDevice());
					data.setDeviceName(detail.getDeviceName());
					data.setTask(detail.getTask());
					if (detail.getDateInit() != null) {
						Date fecha = new Date(detail.getDateInit());
						data.setDateTask(FORMATO_DATE_LARGO.format(fecha));
					}					
					data.setStatusDetail(getStatusNameDetail(detail.getStatus()));
					data.setDescription(detail.getDescription());
					report.add(data);
				}
			}
		}
		return report;
	}
	
	private String getStatusName(int status) {
		String estado = "";
		switch(status) {
			case CERO:
				estado = WAITING;
				break;
			case UNO:
				estado = FAILED;
				break;
			case DOS:
				estado = SUCCESS;
				break;
			default:
				break;
		}
		return estado;
	}
	
	private String getStatusNameDetail(int status) {
		String estado = "";
		switch(status) {
			case UNO:
				estado = FAILED;
				break;
			case DOS:
				estado = SUCCESS;
				break;
			default:
				break;
		}
		return estado;
	}

	@Override
	public ResponseEntity<Object> findAllByAccount(String token, int idAccount) {
		try {
			if (validarToken(token)) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.HOUR, -24);
				List<Installation> installations = installationRepository.findByIdAccountAllAndDate(idAccount,cal.getTimeInMillis());
				List<InstallationForm> forms = new ArrayList<InstallationForm>();
				if (installations.size() > 0) {
					for(Installation install : installations) {
						InstallationForm form = castEntityForm(install);
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

}
