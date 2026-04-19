package com.example.backend;

import java.util.List;

import com.example.ejb.BeneficioService;
import com.example.ejb.dto.BeneficioDTO;
import com.example.ejb.dto.TransferDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Benefícios", description = "Gerenciamento de benefícios")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

  private final BeneficioService ejb;

  public BeneficioController(BeneficioService ejb) {
    this.ejb = ejb;
  }

  @Operation(summary = "Listar todos os benefícios")
  @GetMapping
  public ResponseEntity<List<BeneficioDTO>> list() {
    return ResponseEntity.ok(this.ejb.findAll());
  }

  @Operation(summary = "Buscar benefício por ID")
  @GetMapping("/{id}")
  public ResponseEntity<BeneficioDTO> findById(@PathVariable Long id) {
    return ResponseEntity.ok(this.ejb.findById(id));
  }

  @Operation(summary = "Atualizar benefício")
  @PutMapping("/{id}")
  public ResponseEntity<BeneficioDTO> update(@PathVariable Long id, @RequestBody BeneficioDTO dto) {
    return ResponseEntity.ok(this.ejb.update(id, dto));
  }

  @Operation(summary = "Criar benefício")
  @PostMapping
  public ResponseEntity<BeneficioDTO> create(@RequestBody BeneficioDTO dto) {
    return ResponseEntity.status(201).body(this.ejb.create(dto));
  }

  @Operation(summary = "Excluir benefício")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    this.ejb.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Transferir valor entre benefícios")
  @PostMapping("/transfer")
  public ResponseEntity<Void> transfer(@RequestBody TransferDTO dto) {
    this.ejb.transfer(dto.getFromId(), dto.getToId(), dto.getAmount());
    return ResponseEntity.noContent().build();
  }

}
