package org.olx.banking.service;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.olx.banking.api.TransactionLogDTO;
import org.olx.banking.model.TransactionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransactionLogService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private String filePath;

	public TransactionLogService(@Value("${appender.file.path}") String filePath) {
		super();
		this.filePath = filePath;
	}

	public synchronized void appendTransactionLog(TransactionLogDTO transactionLogDTO, TransactionStatus transactionStatus) {
		try {
			LOGGER.info("appending transaction log for transaction {}", transactionLogDTO);
			Files.write(Paths.get(filePath), buildLog(transactionLogDTO, transactionStatus).getBytes(),
					StandardOpenOption.APPEND, StandardOpenOption.CREATE);
		} catch (IOException e) {
			LOGGER.error("Problem occurs when writting log to file : " + filePath, e);
		}
	}

	private String buildLog(TransactionLogDTO transaction, TransactionStatus transactionStatus) {
		StringBuffer sb = new StringBuffer();
					
		sb.append(transaction.getDate());
		sb.append(",");
		sb.append(transaction.getDestinationAccountId());
		sb.append(",");
		sb.append(transaction.getOriginAccountId());
		sb.append(",");	
		sb.append(transaction.getTransferAmount());
		sb.append(",");	
		sb.append(transaction.getOriginTax());
		sb.append(",");			
		sb.append(transactionStatus.name());
		sb.append("\n");
		return sb.toString();
	}
}
