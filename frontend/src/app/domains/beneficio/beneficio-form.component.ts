import { Component, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BeneficioRepository } from './beneficio.repository';

@Component({
  selector: 'app-beneficio-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './beneficio-form.component.html',
  styleUrl: './beneficio-form.component.scss',
})
export class BeneficioFormComponent implements OnInit {
  private readonly repo = inject(BeneficioRepository);
  private readonly fb = inject(FormBuilder);
  readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);

  isEdit = signal(false);
  id = signal<number | null>(null);

  form = this.fb.group({
    nome: ['', Validators.required],
    descricao: [''],
    ativo: [true],
    valor: [0, [Validators.required, Validators.min(0)]],
  });

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit.set(true);
      this.id.set(Number(id));
      this.repo.findById(Number(id)).subscribe((b) => {
        this.form.patchValue(b);
      });
    }
  }

  submit(): void {
    if (this.form.invalid) return;

    const payload = { id: this.id(), ...this.form.getRawValue() } as any;

    const request$ = this.isEdit()
      ? this.repo.update(this.id()!, payload)
      : this.repo.create(payload);

    request$.subscribe(() => this.router.navigate(['/beneficios']));
  }
}
