package org.olx.banking.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.olx.banking.api.AccountDTO;

@Entity
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String accountId;
	private String bankName;
	private String originCountry;
	private BigDecimal balance;
	@OneToMany(mappedBy="originAccount")
	private List<Transaction> transactions;

	public AccountDTO createFrom(Account account) {
		AccountDTO result = new AccountDTO();
		result.setAccountId(account.getAccountId());
		result.setBalance(account.getBalance());
		result.setBankName(account.getBankName());
		result.setOriginCountry(account.getOriginCountry());
		result.setTransactions(account.getTransactions().stream().map(t -> t.createFrom(t)).collect(Collectors.toList()));
		return result;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getBankName() {
		return bankName;
	}

	public String getOriginCountry() {
		return originCountry;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public Long getId() {
		return id;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", accountId=" + accountId + ", bankName=" + bankName + ", originCountry="
				+ originCountry + ", balance=" + balance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result + ((balance == null) ? 0 : balance.hashCode());
		result = prime * result + ((bankName == null) ? 0 : bankName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Account other = (Account) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (balance == null) {
			if (other.balance != null)
				return false;
		} else if (!balance.equals(other.balance))
			return false;
		if (bankName == null) {
			if (other.bankName != null)
				return false;
		} else if (!bankName.equals(other.bankName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (originCountry == null) {
			if (other.originCountry != null)
				return false;
		} else if (!originCountry.equals(other.originCountry))
			return false;
		return true;
	}

}