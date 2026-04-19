package com.example.ejb.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransferDTO {
  private Long fromId;
  private Long toId;
  private BigDecimal amount;
}
