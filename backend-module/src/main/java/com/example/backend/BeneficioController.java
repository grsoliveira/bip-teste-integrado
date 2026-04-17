package com.example.backend;

import java.util.List;

import com.example.ejb.BeneficioService;
import com.example.ejb.dto.BeneficioDTO;
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

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

  private final BeneficioService ejb;

  public BeneficioController(BeneficioService ejb) {
    this.ejb = ejb;
  }

  @GetMapping
  public ResponseEntity<List<BeneficioDTO>> list() {
    return ResponseEntity.ok(this.ejb.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<BeneficioDTO> findById(@PathVariable Long id) {
    return ResponseEntity.ok(this.ejb.findById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<BeneficioDTO> update(@PathVariable Long id, @RequestBody BeneficioDTO dto) {
    return ResponseEntity.ok(this.ejb.update(id, dto));
  }

  @PostMapping
  public ResponseEntity<BeneficioDTO> create(@RequestBody BeneficioDTO dto) {
    return ResponseEntity.status(201).body(this.ejb.create(dto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    this.ejb.delete(id);
    return ResponseEntity.noContent().build();
  }

}
