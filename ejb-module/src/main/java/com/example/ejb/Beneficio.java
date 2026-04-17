package com.example.ejb;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "beneficio")
@Getter
public class Beneficio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Setter
  @Column(name = "NOME", length = 100)
  private String nome;

  @Setter
  @Column(name = "DESCRICAO")
  private String descricao;

  @Setter
  @Column(name = "ATIVO", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
  private Boolean ativo = true;

  //TODO revisar - já inserindo flag para o optimistic locking
  @Version
  @Column(name = "VERSION")
  private Long version;

  @Setter
  @Column(name = "VALOR", nullable = false, columnDefinition = "DECIMAL(15, 2)")
  private BigDecimal valor;

}
