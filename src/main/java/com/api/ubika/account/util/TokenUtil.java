package com.api.ubika.account.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class TokenUtil {
	
	private static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public String generarToken() {
		return UUID.randomUUID().toString().toUpperCase();
	}
	
	public String generarFecha() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 5);
		return FORMATO_FECHA.format(calendar.getTime());
	}
	
	public boolean validarFecha(String fecha) {
		try {
			Date fechaActual = new Date();
			Date fechaComparar = FORMATO_FECHA.parse(fecha);
			return fechaComparar.before(fechaActual);
		} catch (Exception e) {
			return false;
		}		
	}

}
