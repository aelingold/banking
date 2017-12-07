package org.olx.banking.service;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;

import org.olx.banking.api.TransactionTaxDTO;
import org.olx.banking.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransactionTaxService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
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
		return internalCalcularImpuesto(transaction.getOriginAccount().getBankName(),
				transaction.getDestinationAccount().getBankName(), transaction.getOriginAccount().getOriginCountry(),
				transaction.getDestinationAccount().getOriginCountry(), transaction.getTransferAmount());
	}

	public BigDecimal calcularImpuesto(TransactionTaxDTO transaction) {
		return internalCalcularImpuesto(transaction.getOriginBankName(), transaction.getDestinationBankName(),
				transaction.getOriginCountry(), transaction.getDestinationCountry(), transaction.getTransferAmount());
	}

	private BigDecimal internalCalcularImpuesto(String originBankName, String destinationBankName, String originCountry,
			String destinationCountry, BigDecimal transferAmount) {
		BigDecimal result;
		if (originBankName.equals(destinationBankName)) {
			result = BigDecimal.ZERO;
		} else if (originCountry.equals(destinationCountry)) {
			result = percentage(transferAmount, new BigDecimal(nationalPercentage));
		} else {
			result = percentage(transferAmount, new BigDecimal(internationalPercentage));
		}

		LOGGER.info("calcularImpuesto value {}", result);

		return result;
	}

	private BigDecimal percentage(BigDecimal base, BigDecimal pct) {
		return base.multiply(pct).divide(ONE_HUNDRED);
	}
}
