package org.olx.banking.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.olx.banking.api.TransactionDTO;
import org.olx.banking.api.TransactionResponseDTO;
import org.olx.banking.model.Transaction;
import org.olx.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

	private TransactionService transactionService;
	@Resource(name="transactionTaskExecutor")
	private TaskExecutor executor;
	
	@Autowired
	public TransactionController(TransactionService transactionService) {
		super();
		this.transactionService = transactionService;
	}
	
	@PostMapping("/api/transactions")
	public CompletableFuture<Transaction> process(@RequestBody TransactionDTO transactionDTO) {
		return CompletableFuture.supplyAsync(() -> transactionService.process(transactionDTO), executor);
	}	

	@GetMapping("/api/transactions")
	public List<TransactionResponseDTO> getAll() {
		return transactionService.findAll().stream().map(x -> x.createFrom(x)).collect(Collectors.toList());
	}
}
