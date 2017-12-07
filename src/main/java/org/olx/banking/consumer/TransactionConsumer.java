package org.olx.banking.consumer;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.BlockingQueue;

import org.olx.banking.api.TransactionDTO;
import org.olx.banking.service.TransactionInMemoryService;
import org.olx.banking.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionConsumer implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private BlockingQueue<TransactionDTO> transactionQueue;
	private TransactionService transactionService;	
	private TransactionInMemoryService transactionInMemoryService;
	private Boolean inMemoryAccountService;
	
	public TransactionConsumer(BlockingQueue<TransactionDTO> transactionQueue, TransactionService transactionService,
			TransactionInMemoryService transactionInMemoryService, Boolean inMemoryAccountService) {
		super();
		this.transactionQueue = transactionQueue;
		this.transactionService = transactionService;
		this.transactionInMemoryService = transactionInMemoryService;
		this.inMemoryAccountService = inMemoryAccountService;
	}		   

	@Override
    public void run() {
        while(true){
            try {
            	TransactionDTO transaction = transactionQueue.take();
            	if  (inMemoryAccountService){
            		transactionInMemoryService.process(transaction);
            	}else{
            		transactionService.process(transaction);	
            	}
            	
            } catch (InterruptedException e) {
            	LOGGER.error("InterruptedException ocurred when executing transaction consumer",e);
            } catch (Exception e) {
            	LOGGER.error(e.getMessage(),e);
            }
        }
    }
}
