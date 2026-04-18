import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'beneficios',
    loadChildren: () =>
      import('./domains/beneficio/beneficio.routes').then(m => m.BENEFICIO_ROUTES),
  },
  {
    path: '',
    redirectTo: 'beneficios',
    pathMatch: 'full',
  },
];
