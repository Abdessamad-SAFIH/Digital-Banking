package org.enset.ebankigbackend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class DebitDTO {
    private String accountId;
    private double amount;
    private String description;
}
