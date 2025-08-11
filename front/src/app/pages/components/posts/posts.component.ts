import { Component, OnInit } from '@angular/core';
import { PostService } from 'src/app/core/services/post.service';
import { Post } from 'src/app/shared/models/post.model';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss'],
})
export class PostsComponent implements OnInit {
  posts: Post[] = [];
  loading: boolean = false;

  constructor(private postService: PostService) {}

  ngOnInit(): void {
    this.loadPosts();
  }

  loadPosts(): void {
    this.loading = true;
    this.postService.getUserFeed().subscribe({
      next: (posts) => {
        this.posts = posts;
        this.loading = false;
        console.log('Posts loaded:', posts);
      },
      error: (error) => {
        console.error('Error loading posts:', error);
        this.loading = false;
      },
    });
  }
}
