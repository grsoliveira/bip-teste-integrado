import { Observable } from 'rxjs';
import { Beneficio } from './beneficio.model';

export abstract class BeneficioRepository {
  abstract findAll(): Observable<Beneficio[]>;
  abstract findById(id: number): Observable<Beneficio>;
  abstract create(beneficio: Beneficio): Observable<Beneficio>;
  abstract update(id: number, beneficio: Beneficio): Observable<Beneficio>;
  abstract delete(id: number): Observable<void>;
}
