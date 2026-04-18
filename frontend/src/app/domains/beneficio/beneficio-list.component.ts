import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { BeneficioRepository } from './beneficio.repository';
import { Beneficio } from './beneficio.model';

@Component({
  selector: 'app-beneficio-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './beneficio-list.component.html',
})
export class BeneficioListComponent implements OnInit {
  private readonly repo = inject(BeneficioRepository);
  readonly router = inject(Router);

  beneficios = signal<Beneficio[]>([]);
  loading = signal(true);
  error = signal<string | null>(null);

  ngOnInit(): void {
    this.repo.findAll().subscribe({
      next: (data) => {
        this.beneficios.set(data);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Erro ao carregar benefícios.');
        this.loading.set(false);
      },
    });
  }

  delete(id: number): void {
    if (!confirm('Deseja excluir este benefício?')) return;
    this.repo.delete(id).subscribe(() => {
      this.beneficios.update(list => list.filter(b => b.id !== id));
    });
  }
}
