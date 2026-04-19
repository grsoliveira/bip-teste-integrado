package com.example.ejb;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import com.example.ejb.dto.BeneficioDTO;
import com.example.ejb.model.Beneficio;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;

@Stateless
public class BeneficioEjbService implements BeneficioService {

  @PersistenceContext(unitName = "bipPU")
  private EntityManager em;

  private static final Logger log = Logger.getLogger(BeneficioEjbService.class.getName());

  public void transfer(Long fromId, Long toId, BigDecimal amount) {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new EJBException("O valor da transferência deve ser maior que zero.");
    }

    if (fromId.equals(toId)) {
      throw new EJBException("Origem e destino não podem ser o mesmo benefício.");
    }

    Beneficio from = em.find(Beneficio.class, fromId, LockModeType.OPTIMISTIC);
    Beneficio to = em.find(Beneficio.class, toId, LockModeType.OPTIMISTIC);

    if (from == null) {
      throw new EJBException("Benefício de origem não encontrado.");
    }
    if (to == null) {
      throw new EJBException("Benefício de destino não encontrado.");
    }

    if (from.getValor().compareTo(amount) < 0) {
      throw new EJBException("Saldo insuficiente no benefício de origem.");
    }

    from.setValor(from.getValor().subtract(amount));
    to.setValor(to.getValor().add(amount));
  }

  //candidato a migrar para uma abstract class ou interface
  private BeneficioDTO toDTO(Beneficio entity) {
    return BeneficioDTO.builder()
        .id(entity.getId())
        .nome(entity.getNome())
        .descricao(entity.getDescricao())
        .ativo(entity.getAtivo())
        .valor(entity.getValor())
        .build();
  }

  public BeneficioDTO findById(Long id) {
    Beneficio beneficio = this.em.find(Beneficio.class, id);
    if (beneficio == null) {
      throw new EJBException("Beneficio não encontrado: " + id);
    }
    return this.toDTO(beneficio);
  }

  public List<BeneficioDTO> findAll() {
    List<Beneficio> beneficios = this.em.createQuery("SELECT b FROM Beneficio b", Beneficio.class)
        .getResultList();
    return beneficios.stream().map(this::toDTO).toList();
  }

  public BeneficioDTO create(BeneficioDTO dto) {
    Beneficio beneficio = new Beneficio();
    beneficio.setNome(dto.getNome());
    beneficio.setDescricao(dto.getDescricao());
    beneficio.setValor(dto.getValor());
    beneficio.setAtivo(dto.getAtivo() != null ? dto.getAtivo() : true);

    em.persist(beneficio);
    em.flush();
    return this.toDTO(beneficio);
  }

  public BeneficioDTO update(Long id, BeneficioDTO dto) {
    Beneficio beneficio = em.find(Beneficio.class, id);
    if (beneficio == null) {
      throw new EJBException("Beneficio não encontrado: " + id);
    }
    beneficio.setNome(dto.getNome());
    beneficio.setDescricao(dto.getDescricao());
    beneficio.setValor(dto.getValor());
    beneficio.setAtivo(dto.getAtivo());

    return this.toDTO(em.merge(beneficio));
  }

  public void delete(Long id) {
    Beneficio beneficio = em.find(Beneficio.class, id);
    if (beneficio == null) {
      throw new EJBException("Beneficio não encontrado: " + id);
    }
    em.remove(beneficio);
  }

}
