package com.example.ejb.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BeneficioDTO implements Serializable {

  private Long id;
  private String nome;
  private String descricao;
  private Boolean ativo;
  private BigDecimal valor;

}