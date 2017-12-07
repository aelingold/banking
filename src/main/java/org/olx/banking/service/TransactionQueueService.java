package org.olx.banking.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.olx.banking.api.TransactionDTO;
import org.olx.banking.consumer.TransactionConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransactionQueueService {

	@Value("${transaction.consumer.qty}")
	private Integer transactionConsumersQty;

	@Autowired
	private TransactionService transactionService;
	@Autowired
	private TransactionInMemoryService transactionInMemoryService;
	@Resource
	BlockingQueue<TransactionDTO> transactionQueue;
	private ExecutorService executorService;
	@Value("${use.inmemory.account.service}") 
	private Boolean inMemoryAccountService;	

	public Boolean queue(TransactionDTO transactionDTO) {
		return transactionQueue.add(transactionDTO);
	}

	@PostConstruct
	public void init() {
		
		executorService = Executors.newFixedThreadPool(transactionConsumersQty);
		
		for (Integer i = 0; i < transactionConsumersQty; ++i) {
			executorService.execute(new TransactionConsumer(transactionQueue, transactionService, transactionInMemoryService, inMemoryAccountService));
		}
	}

	@PreDestroy
	public void destroy() {
		executorService.shutdownNow();
	}
}
