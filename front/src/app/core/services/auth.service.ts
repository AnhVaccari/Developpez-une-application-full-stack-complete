import { Injectable } from '@angular/core';
import { ApiService } from './api.service';

import { catchError, Observable, tap } from 'rxjs';
import {
  AuthResponse,
  LoginRequest,
  User,
} from 'src/app/shared/models/auth.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly baseUrl = '/auth';

  constructor(private apiService: ApiService) {}

  register(user: User): Observable<AuthResponse> {
    return this.apiService.post<AuthResponse>(`${this.baseUrl}/register`, user);
  }

  login(loginRequest: LoginRequest): Observable<AuthResponse> {
    return this.apiService
      .post<AuthResponse>(`${this.baseUrl}/login`, loginRequest)
      .pipe(
        tap((response) => {
          if (response.token) {
            localStorage.setItem('token', response.token);
          }
        }),
        catchError((error) => {
          throw error;
        })
      );
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }
}
