package org.olx.banking.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.olx.banking.api.TransactionDTO;

public class AccountInMemory {

	private String accountId;
	private String bankName;
	private String originCountry;
	private AtomicReference<BigDecimal> balance;
	private List<TransactionDTO> transactions = new ArrayList<>();

	public AccountInMemory(String accountId, String bankName, String originCountry, AtomicReference<BigDecimal> balance) {
		super();
		this.accountId = accountId;
		this.bankName = bankName;
		this.originCountry = originCountry;
		this.balance = balance;
	}

	public AccountInMemory() {
		super();
	}

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

	public List<TransactionDTO> getTransactions() {
		return transactions;
	}
	
	public void addTransaction(TransactionDTO transaction){
		transactions.add(transaction);
	}

	public void setTransactions(List<TransactionDTO> transactions) {
		this.transactions = transactions;
	}

	public AtomicReference<BigDecimal> getBalance() {
		return balance;
	}

	public void setBalance(AtomicReference<BigDecimal> balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "LocalAccount [accountId=" + accountId + ", bankName=" + bankName + ", originCountry=" + originCountry
				+ ", balance=" + balance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result + ((bankName == null) ? 0 : bankName.hashCode());
		result = prime * result + ((originCountry == null) ? 0 : originCountry.hashCode());
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
		AccountInMemory other = (AccountInMemory) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (bankName == null) {
			if (other.bankName != null)
				return false;
		} else if (!bankName.equals(other.bankName))
			return false;
		if (originCountry == null) {
			if (other.originCountry != null)
				return false;
		} else if (!originCountry.equals(other.originCountry))
			return false;
		return true;
	}
}
