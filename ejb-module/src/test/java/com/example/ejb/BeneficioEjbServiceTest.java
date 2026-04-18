package com.example.ejb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import com.example.ejb.dto.BeneficioDTO;
import com.example.ejb.model.Beneficio;
import jakarta.ejb.EJBException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BeneficioEjbServiceTest {

  @Mock
  private EntityManager em;

  @InjectMocks
  private BeneficioEjbService service;

  private Beneficio beneficio;
  private BeneficioDTO dto;

  @BeforeEach
  void setUp() {
    beneficio = new Beneficio();
    beneficio.setNome("Beneficio A");
    beneficio.setDescricao("Descrição A");
    beneficio.setValor(new BigDecimal("1000.00"));
    beneficio.setAtivo(true);

    dto = BeneficioDTO.builder()
        .nome("Beneficio A")
        .descricao("Descrição A")
        .valor(new BigDecimal("1000.00"))
        .ativo(true)
        .build();
  }

  // -------------------------
  // findById
  // -------------------------

  @Test
  void findById_deveRetornarDTO_quandoEncontrado() {
    when(em.find(Beneficio.class, 1L)).thenReturn(beneficio);

    BeneficioDTO result = service.findById(1L);

    assertThat(result).isNotNull();
    assertThat(result.getNome()).isEqualTo("Beneficio A");
    assertThat(result.getValor()).isEqualByComparingTo("1000.00");
  }

  @Test
  void findById_deveLancarExcecao_quandoNaoEncontrado() {
    when(em.find(Beneficio.class, 99L)).thenReturn(null);

    assertThatThrownBy(() -> service.findById(99L))
        .isInstanceOf(EJBException.class)
        .hasMessageContaining("99");
  }

  // -------------------------
  // findAll
  // -------------------------

  @Test
  void findAll_deveRetornarListaDeDTO() {
    var query = mock(jakarta.persistence.TypedQuery.class);
    when(em.createQuery("SELECT b FROM Beneficio b", Beneficio.class)).thenReturn(query);
    when(query.getResultList()).thenReturn(List.of(beneficio));

    List<BeneficioDTO> result = service.findAll();

    assertThat(result).hasSize(1);
    assertThat(result.get(0).getNome()).isEqualTo("Beneficio A");
  }

  @Test
  void findAll_deveRetornarListaVazia_quandoNaoHaBeneficios() {
    var query = mock(jakarta.persistence.TypedQuery.class);
    when(em.createQuery("SELECT b FROM Beneficio b", Beneficio.class)).thenReturn(query);
    when(query.getResultList()).thenReturn(List.of());

    List<BeneficioDTO> result = service.findAll();

    assertThat(result).isEmpty();
  }

  // -------------------------
  // create
  // -------------------------

  @Test
  void create_devePersistirERetornarDTO() {
    doNothing().when(em).persist(any(Beneficio.class));
    doNothing().when(em).flush();

    BeneficioDTO result = service.create(dto);

    verify(em).persist(any(Beneficio.class));
    verify(em).flush();
    assertThat(result.getNome()).isEqualTo("Beneficio A");
    assertThat(result.getAtivo()).isTrue();
  }

  @Test
  void create_deveDefinirAtivoComoTrue_quandoNaoInformado() {
    dto.setAtivo(null);
    doNothing().when(em).persist(any(Beneficio.class));
    doNothing().when(em).flush();

    BeneficioDTO result = service.create(dto);

    assertThat(result.getAtivo()).isTrue();
  }

  // -------------------------
  // update
  // -------------------------

  @Test
  void update_deveAtualizarERetornarDTO() {
    when(em.find(Beneficio.class, 1L)).thenReturn(beneficio);
    when(em.merge(beneficio)).thenReturn(beneficio);

    BeneficioDTO dtoAtualizado = BeneficioDTO.builder()
        .nome("Beneficio Atualizado")
        .descricao("Nova descrição")
        .valor(new BigDecimal("2000.00"))
        .ativo(false)
        .build();

    BeneficioDTO result = service.update(1L, dtoAtualizado);

    assertThat(result.getNome()).isEqualTo("Beneficio Atualizado");
    assertThat(result.getValor()).isEqualByComparingTo("2000.00");
    assertThat(result.getAtivo()).isFalse();
    verify(em).merge(beneficio);
  }

  @Test
  void update_deveLancarExcecao_quandoNaoEncontrado() {
    when(em.find(Beneficio.class, 99L)).thenReturn(null);

    assertThatThrownBy(() -> service.update(99L, dto))
        .isInstanceOf(EJBException.class)
        .hasMessageContaining("99");

    verify(em, never()).merge(any());
  }

  // -------------------------
  // delete
  // -------------------------

  @Test
  void delete_deveRemoverBeneficio() {
    when(em.find(Beneficio.class, 1L)).thenReturn(beneficio);
    doNothing().when(em).remove(beneficio);

    assertThatCode(() -> service.delete(1L)).doesNotThrowAnyException();

    verify(em).remove(beneficio);
  }

  @Test
  void delete_deveLancarExcecao_quandoNaoEncontrado() {
    when(em.find(Beneficio.class, 99L)).thenReturn(null);

    assertThatThrownBy(() -> service.delete(99L))
        .isInstanceOf(EJBException.class)
        .hasMessageContaining("99");

    verify(em, never()).remove(any());
  }

  // -------------------------
  // transfer
  // -------------------------

  @Test
  void transfer_deveTransferirValorEntreEntidades() {
    Beneficio from = new Beneficio();
    from.setValor(new BigDecimal("1000.00"));

    Beneficio to = new Beneficio();
    to.setValor(new BigDecimal("500.00"));

    when(em.find(Beneficio.class, 1L)).thenReturn(from);
    when(em.find(Beneficio.class, 2L)).thenReturn(to);

    service.transfer(1L, 2L, new BigDecimal("200.00"));

    assertThat(from.getValor()).isEqualByComparingTo("800.00");
    assertThat(to.getValor()).isEqualByComparingTo("700.00");
    verify(em).merge(from);
    verify(em).merge(to);
  }

}