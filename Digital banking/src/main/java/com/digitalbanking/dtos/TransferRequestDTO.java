package com.digitalbanking.dtos;

import lombok.Data;

@Data
public class TransferRequestDTO {
    private String sourceAccountId;
    private String destinationAccountId;
    private double amount;
    private String description;
}
