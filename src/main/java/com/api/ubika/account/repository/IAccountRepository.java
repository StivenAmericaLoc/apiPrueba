package com.api.ubika.account.repository;

import org.springframework.data.repository.CrudRepository;

import com.api.ubika.account.entity.Account;

public interface IAccountRepository extends CrudRepository<Account, Integer> {
	
	Account findByUser(String user);
	

}
