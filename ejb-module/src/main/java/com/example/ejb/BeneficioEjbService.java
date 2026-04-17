package com.example.ejb;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

//TODO revisar necessidade de todas essas tags quando a integração foi feita com o backend.
@Singleton
@Startup
@LocalBean
public class BeneficioEjbService {

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

  @PostConstruct
  public void init() {
    log.info("### TESTANDO EJB ###");
    this.findAll().forEach(b -> log.info(b.getNome()));
  }
}
