package org.olx.banking.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import org.olx.banking.api.TransactionDTO;
import org.olx.banking.exception.InsufficientFundException;
import org.olx.banking.model.AccountInMemory;
import org.olx.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountInMemoryService {

	private Map<String, AccountInMemory> accounts = new ConcurrentHashMap<>();

	@Autowired
	public AccountInMemoryService(AccountRepository accountRepository) {
		super();
		this.accounts.put("S1", new AccountInMemory("S1", "Santander Rio", "Argentina",
				new AtomicReference<BigDecimal>(new BigDecimal(10.00))));
		this.accounts.put("S2", new AccountInMemory("S2", "Santander Rio", "Argentina",
				new AtomicReference<BigDecimal>(new BigDecimal(5.00))));
		this.accounts.put("I1",
				new AccountInMemory("I1", "ICBC", "Argentina", new AtomicReference<BigDecimal>(new BigDecimal(20.00))));
		this.accounts.put("I2",
				new AccountInMemory("I2", "ICBC", "Brasil", new AtomicReference<BigDecimal>(new BigDecimal(20.00))));
	}

	public void withdrawAndDeposit(TransactionDTO transaction, BigDecimal originTax) {

		AccountInMemory originAccount = getAccount(transaction.getOriginAccountId())
				.orElseThrow(() -> new IllegalArgumentException("originAccountId not found"));

		AccountInMemory destinationAccount = getAccount(transaction.getDestinationAccountId())
				.orElseThrow(() -> new IllegalArgumentException("destinationAccountId not found"));

		BigDecimal originBalance = originAccount.getBalance().get().subtract(originTax)
				.subtract(transaction.getTransferAmount());
		if (originBalance.compareTo(BigDecimal.ZERO) < 0) {
			throw new InsufficientFundException("balance due: " + originBalance);
		}

		originAccount.getBalance().updateAndGet(x -> x.subtract(originTax).subtract(transaction.getTransferAmount()));
		destinationAccount.getBalance().updateAndGet(x -> x.add(transaction.getTransferAmount()));

		originAccount.addTransaction(transaction);
	}

	public Optional<AccountInMemory> getAccount(String accountId) {
		return Optional.ofNullable(accounts.get(accountId));
	}

	public List<AccountInMemory> findAll() {
		return new ArrayList<AccountInMemory>(accounts.values());
	}
}
