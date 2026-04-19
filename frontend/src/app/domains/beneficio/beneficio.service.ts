import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BeneficioRepository } from './beneficio.repository';
import { Beneficio } from './beneficio.model';
import { API_BASE_URL } from '../../core/api.config';


@Injectable()
export class BeneficioService extends BeneficioRepository {
  private readonly http = inject(HttpClient);
  private readonly BASE = `${inject(API_BASE_URL)}/beneficios`;

  findAll(): Observable<Beneficio[]> {
    return this.http.get<Beneficio[]>(this.BASE);
  }

  findById(id: number): Observable<Beneficio> {
    return this.http.get<Beneficio>(`${this.BASE}/${id}`);
  }

  create(beneficio: Beneficio): Observable<Beneficio> {
    return this.http.post<Beneficio>(this.BASE, beneficio);
  }

  update(id: number, beneficio: Beneficio): Observable<Beneficio> {
    return this.http.put<Beneficio>(`${this.BASE}/${id}`, beneficio);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.BASE}/${id}`);
  }
}
