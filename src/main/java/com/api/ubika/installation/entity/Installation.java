package com.api.ubika.installation.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Installations")
public class Installation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private Integer idAccount;

	@Column
	private String description;

	@Column
	private String address;

	@Column
	private Long dateInstallation;

	@Column
	private Long dateFinish;
	
	@Column
	private Integer status;
	
	@Column
	private String addresGps;
	
	@Column
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

	public Long getDateInstallation() {
		return dateInstallation;
	}

	public void setDateInstallation(Long dateInstallation) {
		this.dateInstallation = dateInstallation;
	}

	public Long getDateFinish() {
		return dateFinish;
	}

	public void setDateFinish(Long dateFinish) {
		this.dateFinish = dateFinish;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAddresGps() {
		return addresGps;
	}

	public void setAddresGps(String addresGps) {
		this.addresGps = addresGps;
	}

	public String getNameAccount() {
		return nameAccount;
	}

	public void setNameAccount(String nameAccount) {
		this.nameAccount = nameAccount;
	}

}
