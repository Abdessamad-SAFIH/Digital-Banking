package org.enset.ebankigbackend.services;

import jakarta.transaction.Transactional;
import org.enset.ebankigbackend.entities.BankAccount;
import org.enset.ebankigbackend.entities.CurrentAccount;
import org.enset.ebankigbackend.entities.SavingAccount;
import org.enset.ebankigbackend.repositories.BankAccountRepeository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepeository bankAccountRepeository;

     public void consulter(){
        BankAccount bankAccount = bankAccountRepeository.findById("028b25b7-e441-4f7e-ab24-42caef923010").orElse(null);
        if(bankAccount!=null)
        {
            System.out.println("*********************************");
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getCreatedAt());
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getClass().getSimpleName());
            if(bankAccount instanceof CurrentAccount){
                System.out.println("Over Draft =>" + ((CurrentAccount)bankAccount).getOverDraft());
            } else if(bankAccount instanceof SavingAccount){
                System.out.println("Rate =>" + ((SavingAccount)bankAccount).getInterestRate());
            }
            bankAccount.getAccountOperations().forEach(op->{
                System.out.println(op.getType() + "\t" + op.getOperationDate() + "\t" + op.getAmount());
            });
        }
    }
}
