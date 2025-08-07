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
}
