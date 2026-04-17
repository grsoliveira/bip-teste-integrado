package com.example.backend;

import java.util.Arrays;
import java.util.List;

import com.example.ejb.BeneficioService;
import org.springframework.web.bind.annotation.GetMapping;
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
  public List<String> list() {
    try {
      ejb.teste();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Arrays.asList("Beneficio A", "Beneficio B");
  }
}
