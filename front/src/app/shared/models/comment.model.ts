import { Post } from './post.model';

export interface Comment {
  id: number;
  content: string;
  userId: number;
  postId: number;
  createdAt: string;
  updatedAt: string;
  authorUsername: string;
}

export interface CommentRequest {
  content: string;
}

export interface PostWithCommentsResponse {
  post: Post;
  comments: Comment[];
  commentCount: number;
}
