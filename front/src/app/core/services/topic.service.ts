import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';
import { Topic } from 'src/app/shared/models/topic.model';

@Injectable({
  providedIn: 'root',
})
export class TopicService {
  constructor(private apiService: ApiService) {}

  getAllTopics(): Observable<Topic[]> {
    return this.apiService.get<Topic[]>('/topics');
  }

  getUserSubscriptions(): Observable<Topic[]> {
    return this.apiService.get<Topic[]>('/user/subscriptions');
  }

  subscribe(topicId: number): Observable<void> {
    return this.apiService.post<void>(`/user/subscribe`, {
      topicId: topicId,
    });
  }

  unsubscribe(topicId: number): Observable<void> {
    return this.apiService.post<void>(`/user/unsubscribe`, {
      topicId: topicId,
    });
  }
}
