import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { TopicService } from 'src/app/core/services/topic.service';
import { UserService } from 'src/app/core/services/user.service';
import { Topic } from 'src/app/shared/models/topic.model';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss'],
})
export class UserProfileComponent implements OnInit {
  errorMessage = '';
  loading: boolean = true;
  subscribedThemes: Topic[] = [];

  public form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    username: [
      '',
      [Validators.required, Validators.minLength(3), Validators.maxLength(20)],
    ],
    password: [''],
  });

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private userService: UserService,
    private topicService: TopicService,
    private snackBar: MatSnackBar
  ) {
    this.form.get('password')?.valueChanges.subscribe((value) => {
      this.updatePasswordValidation(value);
    });
  }

  ngOnInit(): void {
    this.loadUserProfile();
  }

  private updatePasswordValidation(password: string): void {
    const passwordControl = this.form.get('password');

    if (password && password.trim().length > 0) {
      // Si l'utilisateur tape quelque chose, on valide
      passwordControl?.setValidators([
        Validators.minLength(8),
        Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])/),
      ]);
    } else {
      // Si vide, pas de validation
      passwordControl?.clearValidators();
    }

    passwordControl?.updateValueAndValidity({ emitEvent: false });
  }

  loadUserProfile(): void {
    this.loading = true;

    // Récupérer le profil
    this.userService.getUserProfile().subscribe({
      next: (profile) => {
        // Remplir le formulaire avec les données
        this.form.patchValue({
          username: profile.username,
          email: profile.email,
        });

        // Récupérer les abonnements
        this.subscribedThemes = profile.subscriptions || [];
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      },
    });
  }

  onSubmit(): void {
    if (this.form.valid) {
      const profileData = { ...this.form.value };

      if (!profileData.password?.trim()) {
        delete profileData.password;
      }

      this.userService.updateProfile(profileData).subscribe({
        next: () => {
          // Vider le mot de passe et réinitialiser l'état
          this.form.patchValue({ password: '' });
          this.form.markAsPristine();

          this.snackBar.open('Profil mis à jour avec succès !', 'Fermer', {
            duration: 3000,
          });
        },
        error: () => {
          this.errorMessage = 'Erreur lors de la mise à jour du profil';
          this.snackBar.open(this.errorMessage, 'Fermer', {
            duration: 3000,
          });
        },
      });
    }
  }

  unsubscribe(theme: Topic): void {
    this.topicService.unsubscribe(theme.id).subscribe({
      next: () => {
        this.subscribedThemes = this.subscribedThemes.filter(
          (t) => t.id !== theme.id
        );
        this.snackBar.open(
          `Vous êtes maintenant désabonné de ${theme.name}`,
          'Fermer',
          {
            duration: 3000,
          }
        );
      },
      error: (error) => {
        if (error.status === 204 || error.status === 200) {
          this.subscribedThemes = this.subscribedThemes.filter(
            (t) => t.id !== theme.id
          );
          this.snackBar.open(
            `Vous êtes maintenant désabonné de ${theme.name}`,
            'Fermer',
            {
              duration: 3000,
            }
          );
        } else {
          this.snackBar.open('Erreur lors du désabonnement', 'Fermer', {
            duration: 3000,
          });
        }
      },
    });
  }

  goHome() {
    this.router.navigate(['/']);
  }
}
