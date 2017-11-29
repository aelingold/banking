package org.olx.banking.repository;

import org.olx.banking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>{

	Account findByAccountId(String accountId);
}
