package com.digitalbanking.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SavingAccountDTO extends BankAccountDTO {
    private double interestRate;
}
