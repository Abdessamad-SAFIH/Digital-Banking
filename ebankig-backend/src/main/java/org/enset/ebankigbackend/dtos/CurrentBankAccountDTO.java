package org.enset.ebankigbackend.dtos;


import lombok.Data;
import org.enset.ebankigbackend.enums.AccountStatus;

import java.util.Date;


@Data
public class  CurrentBankAccountDTO extends BankAccountDTO{
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customer;
    private String description;
    private double overDraft;
}
