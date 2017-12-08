package org.olx.banking.config;

import java.util.concurrent.ThreadFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class QueueConfig {

	@Value("${queue.transaction.capacity}")
	private Integer queueCapacity;
	@Value("${queue.transaction.poolsize}")
	private Integer poolsize;
	@Value("${queue.transaction.maxpoolsize}")
	private Integer maxPoolsize;

	@Bean
	public TaskExecutor transactionTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		ThreadFactory threadFactory = new CustomizableThreadFactory("TransactionExecutor-");
		executor.setThreadFactory(threadFactory);
		executor.setCorePoolSize(poolsize);
		executor.setMaxPoolSize(maxPoolsize);
		executor.setQueueCapacity(queueCapacity);
		TaskExecutor taskExecutor = new ConcurrentTaskExecutor(executor);
		executor.initialize();
		return taskExecutor;
	}
}
