import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';
import { Post } from 'src/app/shared/models/post.model';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  constructor(private apiService: ApiService) {}

  // Feed personnalisé (posts des topics abonnés)
  getUserFeed(): Observable<Post[]> {
    return this.apiService.get<Post[]>('/posts/feed');
  }

  // Post spécifique
  getPost(id: number): Observable<Post> {
    return this.apiService.get<Post>(`/posts/${id}`);
  }

  // Créer un post
  createPost(post: any): Observable<Post> {
    return this.apiService.post<Post>('/posts', post);
  }
}
