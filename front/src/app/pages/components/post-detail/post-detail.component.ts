import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { PostService } from 'src/app/core/services/post.service';
import { Comment, CommentRequest } from 'src/app/shared/models/comment.model';
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

  public commentForm = this.fb.group({
    content: ['', [Validators.required, Validators.minLength(3)]],
  });

  constructor(
    private route: ActivatedRoute,
    private postService: PostService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.postId = Number(this.route.snapshot.paramMap.get('id'));
    console.log('Post ID:', this.postId);

    this.loadPost();
  }

  loadPost(): void {
    console.log('🔍 Début loadPost, ID:', this.postId);
    this.postService.getPost(this.postId).subscribe({
      next: (response: any) => {
        // Changez le type temporairement
        console.log(' Réponse complète:', response);

        // Extraire le post de la réponse
        this.post = response.post; // ← Changement ici !
        this.comments = response.comments || []; // Récupérer aussi les commentaires
        this.loading = false;

        console.log('📊 Post extrait:', this.post);
        console.log('📊 Commentaires extraits:', this.comments);
      },
      error: (error) => {
        console.error('Erreur chargement post:', error);
        this.loading = false;
      },
    });
  }

  addComment(): void {
    if (this.commentForm.valid && this.commentForm.value.content) {
      const commentData: CommentRequest = {
        content: this.commentForm.value.content, // On sait qu'il existe car form.valid
      };

      console.log('Ajout commentaire:', commentData);

      this.postService.addComment(this.postId, commentData).subscribe({
        next: (response) => {
          console.log('Commentaire ajouté:', response);
          this.commentForm.reset();
        },
        error: (error) => {
          console.error('Erreur ajout commentaire:', error);
        },
      });
    }
  }
}
