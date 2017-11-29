package org.olx.banking.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.olx.banking.api.TransactionDTO;
import org.olx.banking.api.TransactionResponseDTO;
import org.olx.banking.service.TransactionQueueService;
import org.olx.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

	private TransactionService transactionService;
	private TransactionQueueService transactionQueueService;

	@Autowired
	public TransactionController(TransactionService transactionService,
			TransactionQueueService transactionQueueService) {
		super();
		this.transactionService = transactionService;
		this.transactionQueueService = transactionQueueService;
	}

	@PostMapping("/api/transaction")
	@Async
	public Boolean queue(@RequestBody TransactionDTO transactionDTO) {
		return transactionQueueService.queue(transactionDTO);
	}

	@GetMapping("/api/transactions")
	public List<TransactionResponseDTO> getAll() {
		return transactionService.findAll().stream().map(x -> x.createFrom(x)).collect(Collectors.toList());
	}
}
