package org.enset.ebankigbackend.repositories;

import org.enset.ebankigbackend.entities.BankAccount;
import org.enset.ebankigbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankAccountRepeository extends JpaRepository<BankAccount, String> {
    List<BankAccount> findByCustomerId(Long customerId);
}
