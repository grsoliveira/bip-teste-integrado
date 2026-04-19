package com.example.ejb;

import java.math.BigDecimal;
import java.util.List;

import com.example.ejb.dto.BeneficioDTO;
import jakarta.ejb.Remote;

@Remote
public interface BeneficioService {
  BeneficioDTO findById(Long id);

  List<BeneficioDTO> findAll();

  BeneficioDTO create(BeneficioDTO dto);

  BeneficioDTO update(Long id, BeneficioDTO dto);

  void delete(Long id);

  void transfer(Long fromId, Long toId, BigDecimal amount);
}
