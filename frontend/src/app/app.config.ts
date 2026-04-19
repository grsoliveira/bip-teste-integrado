import { ApplicationConfig } from '@angular/core';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { BENEFICIO_PROVIDERS } from './domains/beneficio/beneficio.providers';
import { API_BASE_URL } from './core/api.config';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideHttpClient(),
    { provide: API_BASE_URL, useValue: 'http://localhost:8080/api/v1' },
    ...BENEFICIO_PROVIDERS,
  ],
};
