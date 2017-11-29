package org.olx.banking.consumer;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.BlockingQueue;

import javax.annotation.Resource;

import org.olx.banking.api.TransactionDTO;
import org.olx.banking.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionConsumer implements Runnable{

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	@Resource
	private BlockingQueue<TransactionDTO> transactionQueue;
	@Autowired
	private TransactionService transactionService;
	
	public TransactionConsumer(BlockingQueue<TransactionDTO> transactionQueue, TransactionService transactionService) {
		super();
		this.transactionQueue = transactionQueue;
		this.transactionService = transactionService;
	}		   

	@Override
    public void run() {
        while(true){
            try {
            	TransactionDTO transaction = transactionQueue.take();
            	transactionService.process(transaction);
            } catch (InterruptedException e) {
            	LOGGER.error("InterruptedException ocurred when executing transaction consumer",e);
            } catch (Exception e) {
            	LOGGER.error(e.getMessage(),e);
            }
        }
    }
}
