package org.olx.banking.service;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.olx.banking.builder.AccountBuilder;
import org.olx.banking.model.Account;
import org.olx.banking.model.InternationalTransaction;
import org.olx.banking.model.NationalTransaction;
import org.olx.banking.model.Transaction;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class TransactionTaxServiceTest {

	@Test
	public void calcularImpuesto_nationalTransaction_onePercentage() {
		Long id = 1L;
		Account originAccount = AccountBuilder.builderWithDefaults().build();
		Account destinationAccount = AccountBuilder.builderWithDefaults().bankName("ICBC").build();
		BigDecimal transferAmount = new BigDecimal("10");
		Transaction transaction = new NationalTransaction(id, originAccount, destinationAccount, transferAmount);
		BigDecimal result = transaction.calcularImpuesto();
		Assert.assertEquals(new BigDecimal("0.1"), result);
	}
	
	@Test
	public void calcularImpuesto_internationalTransaction_fivePercentage() {
		Long id = 1L;
		Account originAccount = AccountBuilder.builderWithDefaults().build();
		Account destinationAccount = AccountBuilder.builderWithDefaults().bankName("ICBC").originCountry("Brasil").build();
		BigDecimal transferAmount = new BigDecimal("10");
		Transaction transaction = new InternationalTransaction(id, originAccount, destinationAccount, transferAmount);
		BigDecimal result = transaction.calcularImpuesto();
		Assert.assertEquals(new BigDecimal("0.5"), result);
	}
	
	@Test
	public void calcularImpuesto_nationalTransactionSameBank_onePercentage() {
		Long id = 1L;
		Account originAccount = AccountBuilder.builderWithDefaults().build();
		Account destinationAccount = AccountBuilder.builderWithDefaults().build();
		BigDecimal transferAmount = new BigDecimal("10");
		Transaction transaction = new NationalTransaction(id, originAccount, destinationAccount, transferAmount);
		BigDecimal result = transaction.calcularImpuesto();
		Assert.assertEquals(new BigDecimal("0"), result);
	}	
	
	@Test
	public void calcularImpuesto_internationalTransactionSameBank_zeroPercentage() {
		Long id = 1L;
		Account originAccount = AccountBuilder.builderWithDefaults().build();
		Account destinationAccount = AccountBuilder.builderWithDefaults().originCountry("Brasil").build();
		BigDecimal transferAmount = new BigDecimal("10");
		Transaction transaction = new InternationalTransaction(id, originAccount, destinationAccount, transferAmount);
		BigDecimal result = transaction.calcularImpuesto();
		Assert.assertEquals(new BigDecimal("0"), result);
	}	
}
