package org.olx.banking.api;

import java.math.BigDecimal;

public class TransactionDTO {

	private String originAccountId;
	private String destinationAccountId;
	private BigDecimal transferAmount;

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

	@Override
	public String toString() {
		return "TransactionDTO [originAccountId=" + originAccountId + ", destinationAccountId=" + destinationAccountId
				+ ", transferAmount=" + transferAmount + "]";
	}
}
