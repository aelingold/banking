package org.olx.banking.service;

import java.math.BigDecimal;

import org.olx.banking.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransactionTaxService {

	private final BigDecimal ONE_HUNDRED = new BigDecimal(100);
	private final Double internationalPercentage;
	private final Double nationalPercentage;
	
	@Autowired
	public TransactionTaxService(@Value("${national.percentage}") Double nationalPercentage,
			@Value("${international.percentage}") Double internationalPercentage) {
		super();
		this.nationalPercentage = nationalPercentage;
		this.internationalPercentage = internationalPercentage;
	}	
	
	public BigDecimal calcularImpuesto(Transaction transaction) {
		BigDecimal result;
		if (transaction.getOriginAccount().getBankName().equals(transaction.getDestinationAccount().getBankName())) {
			result = BigDecimal.ZERO;
		} else if (transaction.getOriginAccount().getOriginCountry()
				.equals(transaction.getDestinationAccount().getOriginCountry())) {
			result = percentage(transaction.getTransferAmount(), new BigDecimal(nationalPercentage));
		} else {
			result = percentage(transaction.getTransferAmount(), new BigDecimal(internationalPercentage));
		}

		return result;
	}

	private BigDecimal percentage(BigDecimal base, BigDecimal pct) {
		return base.multiply(pct).divide(ONE_HUNDRED);
	}
}
