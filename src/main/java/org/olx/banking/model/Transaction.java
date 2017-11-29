package org.olx.banking.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.olx.banking.api.TransactionResponseDTO;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "origin_account_id")
	private Account originAccount;
	@ManyToOne
	@JoinColumn(name = "destination_account_id")
	private Account destinationAccount;
	private BigDecimal transferAmount;
	private BigDecimal taxAmount;

	public Transaction() {
		super();
	}

	public Transaction(Long id, Account originAccount, Account destinationAccount, BigDecimal transferAmount) {
		super();
		this.id = id;
		this.originAccount = originAccount;
		this.destinationAccount = destinationAccount;
		this.transferAmount = transferAmount;
	}

	public TransactionResponseDTO createFrom(Transaction transaction) {
		TransactionResponseDTO result = new TransactionResponseDTO();

		result.setDestinationAccountId(transaction.getDestinationAccount().getAccountId());
		result.setOriginAccountId(transaction.getOriginAccount().getAccountId());
		result.setTransferAmount(transaction.getTransferAmount());
		result.setTaxAmount(transaction.getTaxAmount());

		return result;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setOriginAccount(Account originAccount) {
		this.originAccount = originAccount;
	}

	public void setDestinationAccount(Account destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	public Account getOriginAccount() {
		return originAccount;
	}

	public Account getDestinationAccount() {
		return destinationAccount;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", originAccount=" + originAccount + ", destinationAccount="
				+ destinationAccount + ", transferAmount=" + transferAmount + ", taxAmount=" + taxAmount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destinationAccount == null) ? 0 : destinationAccount.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((originAccount == null) ? 0 : originAccount.hashCode());
		result = prime * result + ((taxAmount == null) ? 0 : taxAmount.hashCode());
		result = prime * result + ((transferAmount == null) ? 0 : transferAmount.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (destinationAccount == null) {
			if (other.destinationAccount != null)
				return false;
		} else if (!destinationAccount.equals(other.destinationAccount))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (originAccount == null) {
			if (other.originAccount != null)
				return false;
		} else if (!originAccount.equals(other.originAccount))
			return false;
		if (taxAmount == null) {
			if (other.taxAmount != null)
				return false;
		} else if (!taxAmount.equals(other.taxAmount))
			return false;
		if (transferAmount == null) {
			if (other.transferAmount != null)
				return false;
		} else if (!transferAmount.equals(other.transferAmount))
			return false;
		return true;
	}
}