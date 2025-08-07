import { Component, OnInit } from '@angular/core';
import { TopicService } from 'src/app/core/services/topic.service';
import { Topic } from 'src/app/shared/models/topic.model';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss'],
})
export class TopicsComponent implements OnInit {
  topics: Topic[] = [];
  loading: boolean = false;

  constructor(private topicService: TopicService) {}

  ngOnInit(): void {
    this.loadTopics();
  }

  loadTopics(): void {
    this.loading = true;
    this.topicService.getAllTopics().subscribe({
      next: (topics) => {
        this.topics = topics;
        this.loading = false;
        console.log('Topics loaded:', topics);
      },
      error: (error) => {
        console.error('Error loading topics:', error);
        this.loading = false;
      },
    });
  }
}
