package com.api.ubika.form;

import java.util.List;

public class InstallationForm {

	private Integer id;
	private Integer idAccount;
	private String description;
	private String address;
	private String dateInstallation;
	private String dateFinish;
	private List<DetailInstallationForm> details;
	private Integer status;
	private String nameAccount;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(Integer idAccount) {
		this.idAccount = idAccount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDateInstallation() {
		return dateInstallation;
	}

	public void setDateInstallation(String dateInstallation) {
		this.dateInstallation = dateInstallation;
	}

	public String getDateFinish() {
		return dateFinish;
	}

	public void setDateFinish(String dateFinish) {
		this.dateFinish = dateFinish;
	}

	public List<DetailInstallationForm> getDetails() {
		return details;
	}

	public void setDetails(List<DetailInstallationForm> details) {
		this.details = details;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNameAccount() {
		return nameAccount;
	}

	public void setNameAccount(String nameAccount) {
		this.nameAccount = nameAccount;
	}

}
