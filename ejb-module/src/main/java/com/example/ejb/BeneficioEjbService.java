package com.example.ejb;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class BeneficioEjbService implements BeneficioService {

  @PersistenceContext(unitName = "bipPU")
  private EntityManager em;

  private static final Logger log = Logger.getLogger(BeneficioEjbService.class.getName());

  public void transfer(Long fromId, Long toId, BigDecimal amount) {
    Beneficio from = em.find(Beneficio.class, fromId);
    Beneficio to = em.find(Beneficio.class, toId);

    // BUG: sem validações, sem locking, pode gerar saldo negativo e lost update
    from.setValor(from.getValor().subtract(amount));
    to.setValor(to.getValor().add(amount));

    em.merge(from);
    em.merge(to);
  }

  public List<Beneficio> findAll() {
    return em.createQuery("SELECT b FROM Beneficio b", Beneficio.class)
        .getResultList();
  }

  public void teste() {
    log.info("Testando o EJB");
  }
}
