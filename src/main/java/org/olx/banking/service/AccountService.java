package org.olx.banking.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.olx.banking.api.TransactionDTO;
import org.olx.banking.exception.InsufficientFundException;
import org.olx.banking.model.Account;
import org.olx.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

	private AccountRepository accountRepository;

	@Autowired
	public AccountService(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}
	
	public synchronized void withdrawAndDeposit(TransactionDTO transaction, BigDecimal originTax){
		Account originAccount = getAccount(transaction.getOriginAccountId())
		.orElseThrow(() -> new IllegalArgumentException("destinactionAccountId not found"));

		BigDecimal originBalance = originAccount.getBalance().subtract(originTax)
				.subtract(transaction.getTransferAmount());
		if (originBalance.compareTo(BigDecimal.ZERO) < 0) {
			throw new InsufficientFundException("balance due: " + originBalance);
		}
		originAccount.setBalance(originBalance);
		accountRepository.save(originAccount);	
		
		Account destinationAccount = getAccount(transaction.getDestinationAccountId())
		.orElseThrow(() -> new IllegalArgumentException("originAccountId not found"));
		
		destinationAccount.setBalance(destinationAccount.getBalance().add(transaction.getTransferAmount()));
		accountRepository.save(destinationAccount);	
		accountRepository.flush();
	}	
	
	public Optional<Account> getAccount(Long id){
		return Optional.ofNullable(accountRepository.findOne(id));
	}
	
	public Optional<Account> getAccount(String accountId){
		return Optional.ofNullable(accountRepository.findByAccountId(accountId));
	}	
	
	public List<Account> findAll(){
		return accountRepository.findAll();
	}	
}
