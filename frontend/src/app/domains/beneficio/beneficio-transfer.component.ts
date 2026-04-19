import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { API_BASE_URL } from '../../core/api.config';
import { BeneficioRepository } from './beneficio.repository';
import { Beneficio } from './beneficio.model';

@Component({
  selector: 'app-beneficio-transfer',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './beneficio-transfer.component.html',
  styleUrl: './beneficio-transfer.component.scss',
})
export class BeneficioTransferComponent implements OnInit {
  private readonly repo = inject(BeneficioRepository);
  private readonly http = inject(HttpClient);
  private readonly baseUrl = inject(API_BASE_URL);
  readonly router = inject(Router);
  private readonly fb = inject(FormBuilder);

  beneficios = signal<Beneficio[]>([]);
  erro = signal<string | null>(null);
  sucesso = signal(false);

  form = this.fb.group({
    fromId: [null, Validators.required],
    toId: [null, Validators.required],
    amount: [null, [Validators.required, Validators.min(0.01)]],
  });

  get fromId() { return this.form.get('fromId'); }
  get toId() { return this.form.get('toId'); }
  get amount() { return this.form.get('amount'); }

  ngOnInit(): void {
    this.repo.findAll().subscribe(data => this.beneficios.set(data));
  }

  submit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.erro.set(null);
    this.sucesso.set(false);

    this.http.post(`${this.baseUrl}/beneficios/transfer`, this.form.getRawValue())
      .subscribe({
        next: () => {
          this.sucesso.set(true);
          this.form.reset();
        },
        error: (err) => {
          this.erro.set(err?.error?.message ?? 'Erro ao realizar transferência.');
        },
      });
  }
}
