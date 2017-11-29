package org.olx.banking.builder;

import java.math.BigDecimal;

import org.olx.banking.model.Account;

public class AccountBuilder {

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderWithDefaults() {
		return new Builder().id(1L).accountId("abc").bankName("Santander").originCountry("Argentina")
				.balance(BigDecimal.TEN);
	}

	public static class Builder {
		private Account instance = new Account();

		public Builder() {
		}

		public Builder id(Long id) {
			instance.setId(id);
			return this;
		}

		public Builder accountId(String accountId) {
			instance.setAccountId(accountId);
			return this;
		}

		public Builder bankName(String bankName) {
			instance.setBankName(bankName);
			return this;
		}

		public Builder originCountry(String originCountry) {
			instance.setOriginCountry(originCountry);
			return this;
		}

		public Builder balance(BigDecimal balance) {
			instance.setBalance(balance);
			return this;
		}

		public Account build() {
			return instance;
		}
	}
}
