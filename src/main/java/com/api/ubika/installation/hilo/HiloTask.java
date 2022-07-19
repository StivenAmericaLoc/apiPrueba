package com.api.ubika.installation.hilo;

import com.api.ubika.installation.service.InstallationService;

public class HiloTask implements Runnable {
		
	private int idInstallation;
	
	public HiloTask(int idInstallation) {
		setIdInstallation(idInstallation);
	}
	
	@Override
	public void run() {
		InstallationService service = new InstallationService();
		service.runTask(getIdInstallation());
	}

	public int getIdInstallation() {
		return idInstallation;
	}

	public void setIdInstallation(int idInstallation) {
		this.idInstallation = idInstallation;
	}
	
	

}
