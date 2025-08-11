import { Component, OnInit } from '@angular/core';
import { Form, FormBuilder, FormGroup, Validators } from '@angular/forms';
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
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadTopics();
  }

  loadTopics(): void {
    this.topicService.getAllTopics().subscribe({
      next: (topics) => {
        this.topics = topics;
        console.log('Topics chargés:', topics);
      },
      error: (error) => {
        console.error('Erreur chargement topics:', error);
      },
    });
  }

  onSubmit(): void {
    if (this.postForm.valid) {
      const postData = {
        ...this.postForm.value,
        topicId: Number(this.postForm.value.topicId), // Convertir en nombre
      };

      console.log('Données envoyées:', postData);

      this.postService.createPost(postData).subscribe({
        next: (response) => {
          console.log('Post créé:', response);
          this.router.navigate(['/posts']);
        },
        error: (error) => {
          console.error('Erreur création:', error);
        },
      });
    }
  }
}
