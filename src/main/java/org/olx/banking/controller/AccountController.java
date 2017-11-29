package org.olx.banking.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.olx.banking.api.AccountDTO;
import org.olx.banking.model.Account;
import org.olx.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

	private AccountService accountService;
	
	@Autowired
	public AccountController(AccountService accountService) {
		super();
		this.accountService = accountService;
	}

	@GetMapping("api/account/{id}")
	public Optional<AccountDTO> getAccount(@PathVariable Long id){
		Optional<Account> account = accountService.getAccount(id);
		return Optional.of(account.map(x -> x.createFrom(x))).orElse(Optional.empty());
	}
	
	@GetMapping("api/account-id/{id}")
	public Optional<AccountDTO> getAccountId(@PathVariable String id){
		Optional<Account> account = accountService.getAccount(id);
		return Optional.of(account.map(x -> x.createFrom(x))).orElse(Optional.empty());
	}	
	
	@GetMapping("api/accounts")
	public List<AccountDTO> getAll(){
		return accountService.findAll().stream().map(x -> x.createFrom(x)).collect(Collectors.toList());
	}	
}
