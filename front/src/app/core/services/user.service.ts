import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from './api.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private apiService: ApiService) {}

  getUserProfile(): Observable<any> {
    return this.apiService.get<any>('/user/profile');
  }

  updateProfile(profileDate: any): Observable<any> {
    return this.apiService.put<any>('/user/profile', profileDate);
  }
}
