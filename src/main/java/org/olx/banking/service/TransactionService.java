package org.olx.banking.service;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.olx.banking.api.TransactionDTO;
import org.olx.banking.api.TransactionLogDTO;
import org.olx.banking.exception.InsufficientFundException;
import org.olx.banking.exception.InvalidAccountException;
import org.olx.banking.model.Transaction;
import org.olx.banking.model.TransactionStatus;
import org.olx.banking.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private TransactionRepository transactionRepository;
	private TransactionLogService transactionLogService;
	private TransactionTaxService transactionTaxService;
	private AccountService accountService;

	@Autowired
	public TransactionService(TransactionRepository transactionRepository, TransactionLogService transactionLogService,
			AccountService accountService, TransactionTaxService transactionTaxService) {
		super();
		this.transactionRepository = transactionRepository;
		this.transactionLogService = transactionLogService;
		this.accountService = accountService;
		this.transactionTaxService = transactionTaxService;
	}

	public Transaction process(TransactionDTO transactionDTO) {

		LOGGER.info("persisting transaction {}", transactionDTO);

		Transaction transaction;
		try {
			transaction = buildTransaction(transactionDTO);
			
			BigDecimal originTax = transactionTaxService.calcularImpuesto(transaction);
			transaction.setTaxAmount(originTax);
			transaction = transactionRepository.save(transaction);
			
			accountService.withdrawAndDeposit(transactionDTO, originTax);

			TransactionLogDTO transactionLog = buildTransactionLog(transactionDTO, originTax);
			transactionLogService.appendTransactionLog(transactionLog, TransactionStatus.OK);
			return transaction;
		} catch (InsufficientFundException e) {
			TransactionLogDTO transactionLog = buildTransactionLog(transactionDTO, BigDecimal.ZERO);
			transactionLogService.appendTransactionLog(transactionLog, TransactionStatus.INSUFFICIENT_FUNDS);
			throw e;
		} catch (InvalidAccountException e) {
			TransactionLogDTO transactionLog = buildTransactionLog(transactionDTO, BigDecimal.ZERO);
			transactionLogService.appendTransactionLog(transactionLog, TransactionStatus.INVALID_ACCOUNT);
			throw e;
		} catch (Exception e) {
			LOGGER.error("An exception occured when persisting transaction", e);
			TransactionLogDTO transactionLog = buildTransactionLog(transactionDTO, BigDecimal.ZERO);
			transactionLogService.appendTransactionLog(transactionLog, TransactionStatus.ERROR);
			throw new RuntimeException(e);
		}
	}

	private TransactionLogDTO buildTransactionLog(TransactionDTO transactionDTO, BigDecimal originTax) {
		TransactionLogDTO result = new TransactionLogDTO();

		result.setDate(LocalDateTime.now());
		result.setDestinationAccountId(transactionDTO.getDestinationAccountId());
		result.setOriginAccountId(transactionDTO.getOriginAccountId());
		result.setOriginTax(originTax);
		result.setTransferAmount(transactionDTO.getTransferAmount());

		return result;
	}

	private Transaction buildTransaction(TransactionDTO transactionDTO) {
		Transaction transaction = new Transaction();

		transaction.setDestinationAccount(accountService.getAccount(transactionDTO.getDestinationAccountId())
				.orElseThrow(() -> new InvalidAccountException("destinactionAccountId not found")));
		transaction.setOriginAccount(accountService.getAccount(transactionDTO.getOriginAccountId())
				.orElseThrow(() -> new InvalidAccountException("originAccountId not found")));
		transaction.setTransferAmount(transactionDTO.getTransferAmount());

		return transaction;
	}

	public List<Transaction> findAll() {
		return transactionRepository.findAll();
	}
}
