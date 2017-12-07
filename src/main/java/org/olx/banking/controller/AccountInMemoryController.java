package org.olx.banking.controller;

import java.util.List;

import org.olx.banking.model.AccountInMemory;
import org.olx.banking.service.AccountInMemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountInMemoryController {

	private AccountInMemoryService accountInMemoryService;

	@Autowired
	public AccountInMemoryController(AccountInMemoryService accountInMemoryService) {
		super();
		this.accountInMemoryService = accountInMemoryService;
	}
	
	@GetMapping("api-in-memory/accounts")
	public List<AccountInMemory> getAllLocal() {
		return accountInMemoryService.findAll();
	}	
}
