import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { Observable } from 'rxjs';
import { Post } from 'src/app/shared/models/post.model';
import {
  CommentRequest,
  PostWithCommentsResponse,
} from 'src/app/shared/models/comment.model';

@Injectable({
  providedIn: 'root',
})
export class PostService {
  constructor(private apiService: ApiService) {}

  // Feed personnalisé (posts des topics abonnés)
  getUserFeed(): Observable<Post[]> {
    return this.apiService.get<Post[]>('/posts/feed');
  }

  // Voir un post spécifique
  getPost(id: number): Observable<PostWithCommentsResponse> {
    return this.apiService.get<PostWithCommentsResponse>(`/posts/${id}`);
  }

  // Créer un post
  createPost(post: any): Observable<Post> {
    return this.apiService.post<Post>('/posts', post);
  }

  getComments(postId: number): Observable<Comment[]> {
    return this.apiService.get<Comment[]>(`/posts/${postId}/comments`);
  }

  addComment(
    postId: number,
    commentRequest: CommentRequest
  ): Observable<Comment> {
    return this.apiService.post<Comment>(
      `/posts/${postId}/comments`,
      commentRequest
    );
  }
}
