import { Component, OnInit } from '@angular/core';
import { forkJoin } from 'rxjs';
import { TopicService } from 'src/app/core/services/topic.service';
import { Topic } from 'src/app/shared/models/topic.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss'],
})
export class TopicsComponent implements OnInit {
  topics: Topic[] = [];
  loading: boolean = false;

  constructor(
    private topicService: TopicService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.loadTopics();
  }

  loadTopics(): void {
    this.loading = true;

    forkJoin({
      topics: this.topicService.getAllTopics(),
      subscriptions: this.topicService.getUserSubscriptions(),
    }).subscribe({
      next: ({ topics, subscriptions }) => {
        console.log('Topics:', topics);
        console.log('Subscriptions:', subscriptions);

        // Créer un Set des IDs auxquels l'utilisateur est abonné
        const subscribedIds = new Set(subscriptions.map((sub) => sub.id));

        this.topics = topics.map((topic) => ({
          ...topic,
          isSubscribed: subscribedIds.has(topic.id),
        }));

        this.loading = false;
        console.log('Topics avec statut:', this.topics);
      },
      error: (error) => {
        console.error('Erreur:', error);
        this.loading = false;
      },
    });
  }

  subscribe(topic: Topic): void {
    console.log('Abonnement au topic:', topic);

    this.topicService.subscribe(topic.id).subscribe({
      next: (response) => {
        console.log('Abonnement réussi:', response);
        // Mettre à jour l'état local
        topic.isSubscribed = true;
        this.snackBar.open(`Abonné à ${topic.name} !`, 'Fermer', {
          duration: 3000,
        });
      },
      error: (error) => {
        if (error.status === 400) {
          // Déjà abonné
          topic.isSubscribed = true;
          console.log('Utilisateur déjà abonné');
          this.snackBar.open('Vous êtes déjà abonné !', 'Fermer', {
            duration: 3000,
          });
        } else {
          console.error('Erreur abonnement:', error);
          this.snackBar.open("Erreur lors de l'abonnement", 'Fermer', {
            duration: 3000,
          });
        }
      },
    });
  }

  unsubscribe(topic: Topic): void {
    console.log('Désabonnement du topic:', topic);

    this.topicService.unsubscribe(topic.id).subscribe({
      next: (response) => {
        console.log('Désabonnement réussi:', response);
        // Mettre à jour l'état local
        topic.isSubscribed = false;
        this.snackBar.open(`Désabonné de ${topic.name}`, 'Fermer', {
          duration: 3000,
        });
      },
      error: (error) => {
        console.error('Erreur désabonnement:', error);
        // Même en cas d'erreur, on recharge pour vérifier le vrai état
        this.loadTopics(); // Recharge tout depuis la BDD
        this.snackBar.open('Action effectuéee', 'Fermer', {
          duration: 2000,
        });
      },
    });
  }
}
