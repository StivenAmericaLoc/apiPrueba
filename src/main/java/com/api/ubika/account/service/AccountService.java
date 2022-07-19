package com.api.ubika.account.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ubika.account.entity.Account;
import com.api.ubika.account.entity.AccountToken;
import com.api.ubika.account.repository.IAccountRepository;
import com.api.ubika.account.repository.IAccountTokenRepository;
import com.api.ubika.account.util.TokenUtil;
import com.api.ubika.form.AccountForm;
import com.api.ubika.form.LoginForm;

@Service
public class AccountService implements IAccountService {
	
	private static final String ACCOUNT_NOT_FOUND = "Account not found";
	private static final String USER_INVALID = "User not found";
	private static final String PASS_INVALID = "Password not found";
	private static final String CHECK_TOKEN = "Check token";
	
	@Autowired
	private IAccountRepository accountRepository;
	
	@Autowired
	private IAccountTokenRepository tokenRepository;

	@Override
	public ResponseEntity<Object> save(String token, AccountForm form) {
		try {
			if (validarToken(token)) {
				Account account = castFormEntity(form);
				if (account.getId() == null) {
					int cantidad = (int) accountRepository.count();
					account.setId(cantidad+1);
				}
				account = accountRepository.save(account);
				AccountToken accToken = new AccountToken();
				TokenUtil utilToken = new TokenUtil();
				accToken.setIdAccount(account.getId());
				accToken.setToken(utilToken.generarToken());
				accToken.setDateExpired(utilToken.generarFecha());
				tokenRepository.save(accToken);
				return new ResponseEntity<Object>(account, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(CHECK_TOKEN, HttpStatus.FORBIDDEN);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}

	@Override
	public ResponseEntity<Object> delete(String token, Integer id) {
		try {
			if (validarToken(token)) {
				Account account = accountRepository.findById(id).get();
				if (account == null) {
					return new ResponseEntity<Object>(ACCOUNT_NOT_FOUND, HttpStatus.BAD_REQUEST);
				} else {
					accountRepository.delete(account);
					return new ResponseEntity<Object>(account, HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<Object>(CHECK_TOKEN, HttpStatus.FORBIDDEN);
			}		
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> findById(String token, Integer id) {
		try {
			if (validarToken(token)) {
				Account account = accountRepository.findById(id).get();
				if (account == null) {
					return new ResponseEntity<Object>(ACCOUNT_NOT_FOUND, HttpStatus.BAD_REQUEST);
				} else {
					return new ResponseEntity<Object>(account, HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<Object>(CHECK_TOKEN, HttpStatus.FORBIDDEN);
			}				
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<Object> findAll(String token) {
		try {
			if (validarToken(token)) {
				List<Account> accounts = new ArrayList<Account>();
				accountRepository.findAll().forEach(accounts::add);
				return new ResponseEntity<Object>(accounts, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(CHECK_TOKEN, HttpStatus.FORBIDDEN);
			}			
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private Account castFormEntity(AccountForm form) {
		Account entity = new Account();
		if (form.getId() != null) {
			entity.setId(form.getId());
		}
		entity.setName(form.getName());
		entity.setLastName(form.getLastName());
		entity.setAddress(form.getAddress());
		entity.setEmail(form.getEmail());
		entity.setUser(form.getUser());
		entity.setPassword(form.getPassword());
		entity.setProfile(form.getProfile());
		return entity;
	}

	@Override
	public ResponseEntity<Object> login(LoginForm form) {
		try {
			Account account = accountRepository.findByUser(form.getUser());
			if (account == null) {
				return new ResponseEntity<Object>(USER_INVALID, HttpStatus.FORBIDDEN);
			} else {
				if (account.getPassword().equals(form.getPassword())) {
					form.setPassword(null);
					form.setProfile(account.getProfile());
					form.setIdUser(account.getId());
					AccountToken token = tokenRepository.findById(account.getId()).get();
					TokenUtil util = new TokenUtil();
					if (!util.validarFecha(token.getDateExpired())) {
						token.setToken(util.generarToken());
						token.setDateExpired(util.generarFecha());
						tokenRepository.save(token);
					}
					form.setToken(token.getToken());
					return new ResponseEntity<Object>(form, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(PASS_INVALID, HttpStatus.FORBIDDEN);
				}
			}			
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public ResponseEntity<Object> tokenValidation(String token) {
		try {
			if (validarToken(token)) {
				return new ResponseEntity<Object>(HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(CHECK_TOKEN, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private boolean validarToken(String token) {
		try {
			TokenUtil util = new TokenUtil();
			AccountToken info = tokenRepository.findByToken(token);
			boolean valido = util.validarFecha(info.getDateExpired());
			return valido;
		} catch (Exception e) {
			return false;
		}
	}

	

}
