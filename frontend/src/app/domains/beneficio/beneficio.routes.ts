import { Routes } from '@angular/router';

export const BENEFICIO_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./beneficio-list.component').then(m => m.BeneficioListComponent),
  },
  {
    path: 'novo',
    loadComponent: () =>
      import('./beneficio-form.component').then(m => m.BeneficioFormComponent),
  },
  {
    path: ':id/editar',
    loadComponent: () =>
      import('./beneficio-form.component').then(m => m.BeneficioFormComponent),
  },
  {
    path: 'transfer',
    loadComponent: () =>
      import('./beneficio-transfer.component').then(m => m.BeneficioTransferComponent),
  },
];
