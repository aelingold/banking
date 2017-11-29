package org.olx.banking.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionLogDTO {

	private LocalDateTime date;
	private String originAccountId;
	private String destinationAccountId;
	private BigDecimal transferAmount;
	private BigDecimal originTax;

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getOriginAccountId() {
		return originAccountId;
	}

	public void setOriginAccountId(String originAccountId) {
		this.originAccountId = originAccountId;
	}

	public String getDestinationAccountId() {
		return destinationAccountId;
	}

	public void setDestinationAccountId(String destinationAccountId) {
		this.destinationAccountId = destinationAccountId;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	public BigDecimal getOriginTax() {
		return originTax;
	}

	public void setOriginTax(BigDecimal originTax) {
		this.originTax = originTax;
	}

	@Override
	public String toString() {
		return "TransactionLogDTO [date=" + date + ", originAccountId=" + originAccountId + ", destinationAccountId="
				+ destinationAccountId + ", transferAmount=" + transferAmount + ", originTax=" + originTax + "]";
	}
}
