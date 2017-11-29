package org.olx.banking.config;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableScheduling
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport{
 
	@Value("${async.core.poolsize}")
	private Integer poolsize;
	@Value("${async.core.maxpoolsize}")
	private Integer maxPoolsize; 
	@Value("${async.core.queuecapacity}")
	private Integer queueCapacity;	
	
    public AsyncConfig(){
    }
 
    @Override
    public Executor getAsyncExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolsize);
        executor.setMaxPoolSize(maxPoolsize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("Banking-");
        executor.initialize();
        return executor;
    }

	public Integer getPoolsize() {
		return poolsize;
	}

	public void setPoolsize(Integer poolsize) {
		this.poolsize = poolsize;
	}

	public Integer getMaxPoolsize() {
		return maxPoolsize;
	}

	public void setMaxPoolsize(Integer maxPoolsize) {
		this.maxPoolsize = maxPoolsize;
	}

	public Integer getQueueCapacity() {
		return queueCapacity;
	}

	public void setQueueCapacity(Integer queueCapacity) {
		this.queueCapacity = queueCapacity;
	}	
}
