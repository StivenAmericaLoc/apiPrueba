package com.api.ubika.account.service;

import org.springframework.http.ResponseEntity;

import com.api.ubika.form.AccountForm;
import com.api.ubika.form.LoginForm;

public interface IAccountService {
	
	ResponseEntity<Object> save(String token, AccountForm form);
	
	ResponseEntity<Object> delete(String token, Integer id);
	
	ResponseEntity<Object> findById(String token, Integer id);
	
	ResponseEntity<Object> findAll(String token);
	
	ResponseEntity<Object> login(LoginForm form);
	
}
