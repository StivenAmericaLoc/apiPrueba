package com.api.ubika.form;

public class DetailInstallationForm {

	private Integer id;
	private Integer idInstallation;
	private Integer idDevice;
	private String task;
	private Integer status;
	private String dateInit;
	private String description;
	private String dateFinish;
	private String deviceName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdInstallation() {
		return idInstallation;
	}

	public void setIdInstallation(Integer idInstallation) {
		this.idInstallation = idInstallation;
	}

	public Integer getIdDevice() {
		return idDevice;
	}

	public void setIdDevice(Integer idDevice) {
		this.idDevice = idDevice;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDateInit() {
		return dateInit;
	}

	public void setDateInit(String dateInit) {
		this.dateInit = dateInit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateFinish() {
		return dateFinish;
	}

	public void setDateFinish(String dateFinish) {
		this.dateFinish = dateFinish;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

}
