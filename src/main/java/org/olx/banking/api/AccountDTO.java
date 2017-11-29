package org.olx.banking.api;

import java.math.BigDecimal;
import java.util.List;

public class AccountDTO {

	private String accountId;
	private String bankName;
	private String originCountry;
	private BigDecimal balance;
	private List<TransactionResponseDTO> transactions;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<TransactionResponseDTO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionResponseDTO> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "AccountDTO [accountId=" + accountId + ", bankName=" + bankName + ", originCountry=" + originCountry
				+ ", balance=" + balance + "]";
	}
}
