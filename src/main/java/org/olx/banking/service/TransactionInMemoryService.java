package org.olx.banking.service;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.olx.banking.api.TransactionDTO;
import org.olx.banking.api.TransactionLogDTO;
import org.olx.banking.api.TransactionTaxDTO;
import org.olx.banking.exception.InsufficientFundException;
import org.olx.banking.exception.InvalidAccountException;
import org.olx.banking.model.AccountInMemory;
import org.olx.banking.model.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionInMemoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private TransactionLogService transactionLogService;
	private TransactionTaxService transactionTaxService;
	private AccountInMemoryService accountInMemoryService;

	@Autowired
	public TransactionInMemoryService(TransactionLogService transactionLogService,
			TransactionTaxService transactionTaxService, AccountInMemoryService accountInMemoryService) {
		super();
		this.transactionLogService = transactionLogService;
		this.transactionTaxService = transactionTaxService;
		this.accountInMemoryService = accountInMemoryService;
	}

	public void process(TransactionDTO transactionDTO) {

		LOGGER.info("persisting transaction {}", transactionDTO);
		
		try {
			TransactionTaxDTO transaction = buildTransaction(transactionDTO);

			BigDecimal originTax = transactionTaxService.calcularImpuesto(transaction);

			accountInMemoryService.withdrawAndDeposit(transactionDTO, originTax);

			TransactionLogDTO transactionLog = buildTransactionLog(transactionDTO, originTax);
			transactionLogService.appendTransactionLog(transactionLog, TransactionStatus.OK);

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

	private TransactionTaxDTO buildTransaction(TransactionDTO transactionDTO) {

		AccountInMemory originAccount = accountInMemoryService.getAccount(transactionDTO.getDestinationAccountId())
				.orElseThrow(() -> new InvalidAccountException("destinactionAccountId not found"));

		AccountInMemory destinationAccount = accountInMemoryService.getAccount(transactionDTO.getOriginAccountId())
				.orElseThrow(() -> new InvalidAccountException("originAccountId not found"));

		TransactionTaxDTO transaction = new TransactionTaxDTO();

		transaction.setDestinationBankName(destinationAccount.getBankName());
		transaction.setDestinationCountry(destinationAccount.getOriginCountry());
		transaction.setOriginBankName(originAccount.getBankName());
		transaction.setOriginCountry(originAccount.getOriginCountry());
		transaction.setTransferAmount(transactionDTO.getTransferAmount());

		return transaction;
	}
}
