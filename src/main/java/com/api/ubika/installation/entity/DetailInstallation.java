package com.api.ubika.installation.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DetailsInstallation")
public class DetailInstallation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private Integer idInstallation;

	@Column
	private Integer idDevice;

	@Column
	private String task;

	@Column
	private Integer status;

	@Column
	private Long dateInit;
	
	@Column
	private String description;

	@Column
	private Long dateFinish;
	
	@Column
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

	public Long getDateInit() {
		return dateInit;
	}

	public void setDateInit(Long dateInit) {
		this.dateInit = dateInit;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getDateFinish() {
		return dateFinish;
	}

	public void setDateFinish(Long dateFinish) {
		this.dateFinish = dateFinish;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	
}
