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
	private Integer taskCoordenate;

	@Column
	private Integer taskOff;

	@Column
	private Integer taskOn;

	@Column
	private Integer taskAccuary;

	@Column
	private Integer taskPanicBoton;

	@Column
	private Integer taskOffRemote;

	@Column
	private String dateInit;

	@Column
	private String dateFinish;

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

	public Integer getTaskCoordenate() {
		return taskCoordenate;
	}

	public void setTaskCoordenate(Integer taskCoordenate) {
		this.taskCoordenate = taskCoordenate;
	}

	public Integer getTaskOff() {
		return taskOff;
	}

	public void setTaskOff(Integer taskOff) {
		this.taskOff = taskOff;
	}

	public Integer getTaskOn() {
		return taskOn;
	}

	public void setTaskOn(Integer taskOn) {
		this.taskOn = taskOn;
	}

	public Integer getTaskAccuary() {
		return taskAccuary;
	}

	public void setTaskAccuary(Integer taskAccuary) {
		this.taskAccuary = taskAccuary;
	}

	public Integer getTaskPanicBoton() {
		return taskPanicBoton;
	}

	public void setTaskPanicBoton(Integer taskPanicBoton) {
		this.taskPanicBoton = taskPanicBoton;
	}

	public Integer getTaskOffRemote() {
		return taskOffRemote;
	}

	public void setTaskOffRemote(Integer taskOffRemote) {
		this.taskOffRemote = taskOffRemote;
	}

	public String getDateInit() {
		return dateInit;
	}

	public void setDateInit(String dateInit) {
		this.dateInit = dateInit;
	}

	public String getDateFinish() {
		return dateFinish;
	}

	public void setDateFinish(String dateFinish) {
		this.dateFinish = dateFinish;
	}

}
