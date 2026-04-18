package com.example.backend;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import com.example.ejb.BeneficioService;
import com.example.ejb.dto.BeneficioDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ejb.EJBException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BeneficioController.class)
class BeneficioControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private BeneficioService ejb;

  private BeneficioDTO dto;

  @BeforeEach
  void setUp() {
    dto = BeneficioDTO.builder()
        .id(1L)
        .nome("Beneficio A")
        .descricao("Descrição A")
        .valor(new BigDecimal("1000.00"))
        .ativo(true)
        .build();
  }

  // GET /api/v1/beneficios

  @Test
  void list_deveRetornar200ComListaDeBeneficios() throws Exception {
    when(ejb.findAll()).thenReturn(List.of(dto));

    mockMvc.perform(get("/api/v1/beneficios"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].nome").value("Beneficio A"))
        .andExpect(jsonPath("$[0].valor").value(1000.00));
  }

  @Test
  void list_deveRetornar200ComListaVazia() throws Exception {
    when(ejb.findAll()).thenReturn(List.of());

    mockMvc.perform(get("/api/v1/beneficios"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(0));
  }

  // GET /api/v1/beneficios/{id}

  @Test
  void findById_deveRetornar200ComDTO() throws Exception {
    when(ejb.findById(1L)).thenReturn(dto);

    mockMvc.perform(get("/api/v1/beneficios/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.nome").value("Beneficio A"))
        .andExpect(jsonPath("$.ativo").value(true));
  }

  @Test
  void findById_deveRetornar404_quandoNaoEncontrado() throws Exception {
    when(ejb.findById(99L)).thenThrow(new EJBException("Beneficio não encontrado: 99"));

    mockMvc.perform(get("/api/v1/beneficios/99"))
        .andExpect(status().isNotFound());
  }

  // POST /api/v1/beneficios

  @Test
  void create_deveRetornar201ComDTOCriado() throws Exception {
    BeneficioDTO input = BeneficioDTO.builder()
        .nome("Novo Beneficio")
        .descricao("Descrição")
        .valor(new BigDecimal("500.00"))
        .ativo(true)
        .build();

    BeneficioDTO criado = BeneficioDTO.builder()
        .id(2L)
        .nome("Novo Beneficio")
        .descricao("Descrição")
        .valor(new BigDecimal("500.00"))
        .ativo(true)
        .build();

    when(ejb.create(any(BeneficioDTO.class))).thenReturn(criado);

    mockMvc.perform(post("/api/v1/beneficios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(input)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(2))
        .andExpect(jsonPath("$.nome").value("Novo Beneficio"));
  }

  // PUT /api/v1/beneficios/{id}

  @Test
  void update_deveRetornar200ComDTOAtualizado() throws Exception {
    BeneficioDTO atualizado = BeneficioDTO.builder()
        .id(1L)
        .nome("Beneficio Atualizado")
        .descricao("Nova descrição")
        .valor(new BigDecimal("2000.00"))
        .ativo(false)
        .build();

    when(ejb.update(eq(1L), any(BeneficioDTO.class))).thenReturn(atualizado);

    mockMvc.perform(put("/api/v1/beneficios/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(atualizado)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Beneficio Atualizado"))
        .andExpect(jsonPath("$.ativo").value(false));
  }

  @Test
  void update_deveRetornar404_quandoNaoEncontrado() throws Exception {
    when(ejb.update(eq(99L), any(BeneficioDTO.class)))
        .thenThrow(new EJBException("Beneficio não encontrado: 99"));

    mockMvc.perform(put("/api/v1/beneficios/99")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isNotFound());
  }

  // DELETE /api/v1/beneficios/{id}

  @Test
  void delete_deveRetornar204() throws Exception {
    doNothing().when(ejb).delete(1L);

    mockMvc.perform(delete("/api/v1/beneficios/1"))
        .andExpect(status().isNoContent());

    verify(ejb).delete(1L);
  }

  @Test
  void delete_deveRetornar404_quandoNaoEncontrado() throws Exception {
    doThrow(new EJBException("Beneficio não encontrado: 99"))
        .when(ejb).delete(99L);

    mockMvc.perform(delete("/api/v1/beneficios/99"))
        .andExpect(status().isNotFound());
  }
}