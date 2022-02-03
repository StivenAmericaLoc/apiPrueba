package com.api.ubika.loggin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ubika.account.service.IAccountService;
import com.api.ubika.form.LoginForm;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*")
public class LoginController {
	
	@Autowired
	private IAccountService accountService;
	
	@GetMapping("")
	public ResponseEntity<Object> login(@RequestBody LoginForm form) {
		return accountService.login(form);
	}

}
