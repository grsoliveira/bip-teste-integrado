import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BeneficioRepository } from './beneficio.repository';
import { Beneficio } from './beneficio.model';

const BASE = 'http://localhost:8080/api/v1/beneficios';

@Injectable()
export class BeneficioService extends BeneficioRepository {
  private readonly http = inject(HttpClient);

  findAll(): Observable<Beneficio[]> {
    return this.http.get<Beneficio[]>(BASE);
  }

  findById(id: number): Observable<Beneficio> {
    return this.http.get<Beneficio>(`${BASE}/${id}`);
  }

  create(beneficio: Beneficio): Observable<Beneficio> {
    return this.http.post<Beneficio>(BASE, beneficio);
  }

  update(id: number, beneficio: Beneficio): Observable<Beneficio> {
    return this.http.put<Beneficio>(`${BASE}/${id}`, beneficio);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${BASE}/${id}`);
  }
}
