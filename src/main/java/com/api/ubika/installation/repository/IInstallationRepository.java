package com.api.ubika.installation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.api.ubika.installation.entity.Installation;

public interface IInstallationRepository extends CrudRepository<Installation, Integer> {
	
	@Query("select ins from Installation ins where ins.idAccount IN (?1)")
	List<Installation> findByIdAccountAll(Integer idAccount);
	
	@Query("select ins from Installation ins where (ins.dateInstallation >= (:date) OR ins.status <= 1)")
	List<Installation> findAllByDate(@Param("date") Long date);
	
	@Query("select ins from Installation ins where ins.dateInstallation >= (:dateInit) and ins.dateInstallation <= (:dateFin)")
	List<Installation> findAllByDateInstallation(@Param("dateInit") Long dateInit, @Param("dateFin") Long dateFin);
	
	@Query("select ins from Installation ins where ins.status = (:status)")
	List<Installation> findAllByStatus(@Param("status") int status);
	
	@Query("select ins from Installation ins where ins.dateInstallation >= (:dateInit) and ins.dateInstallation <= (:dateFin) and ins.status = :status ")
	List<Installation> findAllByDateAndStatus(@Param("dateInit") Long dateInit, @Param("dateFin") Long dateFin, @Param("status") int status);
	
	@Query("select ins from Installation ins where ins.idAccount = (:idAccount) AND (ins.dateInstallation >= (:date) OR ins.status <= 1)")
	List<Installation> findByIdAccountAllAndDate(@Param("idAccount") Integer idAccount, @Param("date") Long date);
}
