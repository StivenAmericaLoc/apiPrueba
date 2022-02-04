package com.api.ubika.account.repository;

import org.springframework.data.repository.CrudRepository;

import com.api.ubika.account.entity.AccountToken;

public interface IAccountTokenRepository extends CrudRepository<AccountToken, Integer> {
	
	AccountToken findByToken(String token);

}
