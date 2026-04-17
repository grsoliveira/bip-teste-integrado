package com.example.backend;

import java.util.List;

import com.example.ejb.BeneficioService;
import com.example.ejb.dto.BeneficioDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

  private final BeneficioService ejb;

  public BeneficioController(BeneficioService ejb) {
    this.ejb = ejb;
  }

  @GetMapping
  public List<BeneficioDTO> list() {
    return this.ejb.findAll();
  }

  @GetMapping("/{id}")
  public BeneficioDTO findById(@PathVariable Long id) {
    return this.ejb.findById(id);
  }

  @PutMapping("/{id}")
  public BeneficioDTO update(@PathVariable Long id, @RequestBody BeneficioDTO dto) {
    return this.ejb.update(id, dto);
  }

  @PostMapping
  public BeneficioDTO create(@RequestBody BeneficioDTO dto) {
    return this.ejb.create(dto);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    this.ejb.delete(id);
  }

}
