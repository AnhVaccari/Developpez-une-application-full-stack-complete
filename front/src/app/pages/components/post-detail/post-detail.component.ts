import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { PostService } from 'src/app/core/services/post.service';
import {
  Comment,
  CommentRequest,
  PostWithCommentsResponse,
} from 'src/app/shared/models/comment.model';
import { Post } from 'src/app/shared/models/post.model';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss'],
})
export class PostDetailComponent implements OnInit {
  post: Post | null = null;
  comments: Comment[] = [];
  loading: boolean = true;
  postId: number = 0;
  commentText: string = '';

  constructor(
    private route: ActivatedRoute,
    private postService: PostService
  ) {}

  ngOnInit(): void {
    this.postId = Number(this.route.snapshot.paramMap.get('id'));

    this.loadPost();
  }

  loadPost(): void {
    this.postService.getPost(this.postId).subscribe({
      next: (response: PostWithCommentsResponse) => {
        // Extraire le post de la réponse
        this.post = response.post;

        // Récupérer aussi les commentaires
        this.comments = response.comments || [];
        this.loading = false;
      },
      error: (error) => {
        this.loading = false;
      },
    });
  }

  addComment(): void {
    if (this.commentText.trim().length >= 3) {
      const commentData = {
        content: this.commentText,
        postId: this.postId,
      };

      this.postService.addComment(this.postId, commentData).subscribe({
        next: (response) => {
          // Vider le champ
          this.commentText = '';

          // Recharger pour voir le nouveau commentaire
          this.loadPost();
        },
        error: (error) => {
          console.error('Erreur:', error);
        },
      });
    }
  }
}
