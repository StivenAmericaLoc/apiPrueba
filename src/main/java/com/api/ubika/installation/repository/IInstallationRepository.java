package com.api.ubika.installation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.api.ubika.installation.entity.Installation;

public interface IInstallationRepository extends CrudRepository<Installation, Integer> {
	
	@Query("select ins from Installation ins where ins.idAccount IN (?1)")
	List<Installation> findByIdAccountAll(Integer idAccount);

}
