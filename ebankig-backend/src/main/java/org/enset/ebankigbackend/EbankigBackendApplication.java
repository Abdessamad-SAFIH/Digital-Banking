package org.enset.ebankigbackend;

import org.enset.ebankigbackend.dtos.BankAccountDTO;
import org.enset.ebankigbackend.dtos.CurrentBankAccountDTO;
import org.enset.ebankigbackend.dtos.CustomerDTO;
import org.enset.ebankigbackend.dtos.SavingBankAccountDTO;
import org.enset.ebankigbackend.entities.*;
import org.enset.ebankigbackend.enums.AccountStatus;
import org.enset.ebankigbackend.enums.OperationType;
import org.enset.ebankigbackend.exceptions.BalanceNotSufficientException;
import org.enset.ebankigbackend.exceptions.BankAccountNotFoundException;
import org.enset.ebankigbackend.exceptions.CustomerNotFoundException;
import org.enset.ebankigbackend.repositories.AccountOperationRepeository;
import org.enset.ebankigbackend.repositories.BankAccountRepeository;
import org.enset.ebankigbackend.repositories.CustomerRepeository;
import org.enset.ebankigbackend.services.BankAccountService;
import org.enset.ebankigbackend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankigBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankigBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
		return args -> {
			//bankService.consulter();
			Stream.of("Hassan", "Mohamed", "Yassin").forEach(name -> {
				CustomerDTO customer = new CustomerDTO();
				customer.setName(name);
				customer.setEmail(name + "@gmail.com");
				bankAccountService.saveCustomer(customer);
			});
			bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000, 9000, customer.getId());
					bankAccountService.saveSavingBankAccount(Math.random()*120000, 5.5, customer.getId());

				} catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
			List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
			for(BankAccountDTO bankAccount:bankAccounts ){
				for(int i = 0; i < 10; i++ ) {
					String accounId;
					if(bankAccount instanceof SavingBankAccountDTO){
						accounId = ((SavingBankAccountDTO) bankAccount).getId();
					}
					else {
						accounId = ((CurrentBankAccountDTO) bankAccount).getId();
					}
					bankAccountService.credit(accounId, 10000+Math.random()*120000, "Credit");
					bankAccountService.debit(accounId, 1000+Math.random()*9000, "Debit");
				}
			}
		};
	}
	//@Bean
	CommandLineRunner start(CustomerRepeository customerRepeository,
							BankAccountRepeository bankAccountRepeository,
							AccountOperationRepeository accountOperationRepeository){
		return args -> {
			Stream.of("Hassan", "Yassin", "Abdessamad").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name + "@gmail.com");
				customerRepeository.save(customer);
			});
			customerRepeository.findAll().forEach(cust ->{
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random()*90000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(cust);
				currentAccount.setOverDraft(9000);
				bankAccountRepeository.save(currentAccount);

				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random()*90000);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(cust);
				savingAccount.setInterestRate(5.5);
				bankAccountRepeository.save(savingAccount);
			});
			bankAccountRepeository.findAll().forEach(acc -> {
				for(int i=0; i<10  ; i++){
					AccountOperation accountOperation = new AccountOperation();
					accountOperation.setOperationDate(new Date());
					accountOperation.setAmount(Math.random()*12000);
					accountOperation.setType(Math.random()>0.5? OperationType.DEBIT : OperationType.CREDIT);
					accountOperation.setBankAccount(acc);
					accountOperationRepeository.save(accountOperation);
				}
			});

		};
	}
}
