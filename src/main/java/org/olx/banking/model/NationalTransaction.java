package org.olx.banking.model;

import java.math.BigDecimal;

import javax.persistence.Entity;

@Entity
public class NationalTransaction extends Transaction{
	
	public NationalTransaction() {
		super();
	}

	public NationalTransaction(Long id, Account originAccount, Account destinationAccount, BigDecimal transferAmount) {
		super(id, originAccount, destinationAccount, transferAmount);
	}

	@Override
	public BigDecimal calcularImpuesto() {
		BigDecimal result;
		if (getOriginAccount().getBankName().equals(getDestinationAccount().getBankName())) {
			result = BigDecimal.ZERO;
		} else {
			result = percentage(getTransferAmount(), new BigDecimal(1.0));
		}
		
		LOGGER.info("calcularImpuesto value {}", result);

		return result;
	}	
}
