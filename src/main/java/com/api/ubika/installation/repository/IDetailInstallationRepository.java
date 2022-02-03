package com.api.ubika.installation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.api.ubika.installation.entity.DetailInstallation;

public interface IDetailInstallationRepository extends CrudRepository<DetailInstallation, Integer> {
	
	@Query("select di from DetailInstallation di where di.idInstallation IN (?1)")
	List<DetailInstallation> findByIdInstallationAll(Integer idInstallation);

}
