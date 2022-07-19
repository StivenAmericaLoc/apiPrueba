package com.api.ubika.loggin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ubika.account.service.IAccountService;
import com.api.ubika.form.LoginForm;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private IAccountService accountService;
	
	@PostMapping("")
	public ResponseEntity<Object> login(@RequestBody LoginForm form) {
		return accountService.login(form);
	}
	
	@GetMapping("/validate/{token}")
	public ResponseEntity<Object> tokenValidation(@PathVariable String token) {
		return accountService.tokenValidation(token);
	}

}
