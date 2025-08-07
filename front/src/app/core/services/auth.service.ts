import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { AuthResponse } from 'src/app/shared/authResponse';
import { User } from 'src/app/shared/models/user.model';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly baseUrl = '/auth';

  constructor(private apiService: ApiService) {}

  register(user: User): Observable<AuthResponse> {
    return this.apiService.post<AuthResponse>(`${this.baseUrl}/register`, user);
  }

  login(credentials: {
    email: string;
    password: string;
  }): Observable<AuthResponse> {
    return this.apiService
      .post<AuthResponse>(`${this.baseUrl}/login`, credentials)
      .pipe(
        tap((response) => {
          localStorage.setItem('token', response.token);
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
