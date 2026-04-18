import { Provider } from '@angular/core';
import { BeneficioRepository } from './beneficio.repository';
import { BeneficioService } from './beneficio.service';

export const BENEFICIO_PROVIDERS: Provider[] = [
  { provide: BeneficioRepository, useClass: BeneficioService },
];
