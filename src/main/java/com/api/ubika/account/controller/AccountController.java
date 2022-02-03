package com.api.ubika.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ubika.account.service.IAccountService;
import com.api.ubika.form.AccountForm;

@RestController
@RequestMapping("/account")
@CrossOrigin(origins = "*")
public class AccountController {
	
	@Autowired
	private IAccountService accountService;
	
	@PostMapping("/save")
	public ResponseEntity<Object> save(@RequestBody AccountForm form, @RequestHeader("Authorization") String token) {
		return accountService.save(token, form);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> delete(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
		return accountService.delete(token, id);
	}
	
	@GetMapping("/all")
	public ResponseEntity<Object> findAll(@RequestHeader("Authorization") String token) {
		return accountService.findAll(token);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
		return accountService.findById(token, id);
	}

}
