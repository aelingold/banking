package org.olx.banking.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.olx.banking.api.TransactionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

	@Value("${queue.transaction.capacity}")
	private Integer queueCapacity;
	
	@Bean(name = "transactionQueue")
	public BlockingQueue<TransactionDTO> transactionQueue() {
		return new ArrayBlockingQueue<>(queueCapacity);
	}
}
