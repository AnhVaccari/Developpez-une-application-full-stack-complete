export interface Post {
  id: number;
  title: string;
  content: string;
  userId: number;
  topicId: number;
  createdAt: string;
  updatedAt: string;
  // Infos enrichies
  authorUsername: string;
  topicName: string;
}

export interface PostRequest {
  title: string;
  content: string;
  topicId: number;
}
