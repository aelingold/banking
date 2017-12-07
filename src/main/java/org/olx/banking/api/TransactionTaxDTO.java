package org.olx.banking.api;

import java.math.BigDecimal;

public class TransactionTaxDTO {

	private String originBankName;
	private String destinationBankName;
	private String originCountry;
	private String destinationCountry;
	private BigDecimal transferAmount;

	public String getOriginBankName() {
		return originBankName;
	}

	public void setOriginBankName(String originBankName) {
		this.originBankName = originBankName;
	}

	public String getDestinationBankName() {
		return destinationBankName;
	}

	public void setDestinationBankName(String destinationBankName) {
		this.destinationBankName = destinationBankName;
	}

	public String getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	public String getDestinationCountry() {
		return destinationCountry;
	}

	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}

	public BigDecimal getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(BigDecimal transferAmount) {
		this.transferAmount = transferAmount;
	}

	@Override
	public String toString() {
		return "TransactionTaxDTO [originBankName=" + originBankName + ", destinationBankName=" + destinationBankName
				+ ", originCountry=" + originCountry + ", destinationCountry=" + destinationCountry
				+ ", transferAmount=" + transferAmount + "]";
	}
}
