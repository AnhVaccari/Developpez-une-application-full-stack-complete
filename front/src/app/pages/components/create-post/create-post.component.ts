import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { PostService } from 'src/app/core/services/post.service';
import { TopicService } from 'src/app/core/services/topic.service';
import { Topic } from 'src/app/shared/models/topic.model';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss'],
})
export class CreatePostComponent implements OnInit {
  topics: Topic[] = [];

  public postForm = this.fb.nonNullable.group({
    topicId: ['', Validators.required],
    title: [
      '',
      [Validators.required, Validators.minLength(5), Validators.maxLength(200)],
    ],
    content: ['', [Validators.required, Validators.minLength(10)]],
  });

  constructor(
    private fb: FormBuilder,
    private postService: PostService,
    private topicService: TopicService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadTopics();
  }

  loadTopics(): void {
    this.topicService.getAllTopics().subscribe({
      next: (topics) => {
        this.topics = topics;
      },
      error: () => {
        this.snackBar.open('Erreur lors du chargement des sujets', 'Fermer', {
          duration: 3000,
        });
      },
    });
  }

  onSubmit(): void {
    if (this.postForm.valid) {
      const postData = {
        ...this.postForm.value,
        topicId: Number(this.postForm.value.topicId), // Convertir en nombre
      };

      this.postService.createPost(postData).subscribe({
        next: () => {
          this.router.navigate(['/posts']);
          this.snackBar.open('Post créé avec succès !', 'Fermer', {
            duration: 3000,
          });
        },
        error: (error) => {
          console.error('Erreur création:', error);
          this.snackBar.open('Erreur lors de la création du post', 'Fermer', {
            duration: 3000,
          });
        },
      });
    }
  }
}
