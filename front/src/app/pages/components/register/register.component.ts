import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ValidationErrors,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  errorMessage = '';

  public form = this.fb.nonNullable.group({
    email: [
      '',
      [Validators.required, Validators.email, this.strictEmailValidator],
    ],
    username: [
      '',
      [Validators.required, Validators.minLength(3), Validators.maxLength(20)],
    ],
    password: [
      '',
      [
        Validators.required,
        Validators.minLength(8),
        this.strongPasswordValidator,
      ],
    ],
  });

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {}

  public submit(): void {
    if (this.form.invalid) return;
    const registerRequest = this.form.getRawValue();
    this.authService.register(registerRequest).subscribe({
      next: () => {
        this.router.navigate(['/login']);
      },
      error: (error) => {
        console.log('Erreur complète:', error); // Pour débugger
        // Afficher le message d'erreur spécifique du serveur si disponible
        if (error.error) {
          if (
            error.error &&
            error.error.errors &&
            error.error.errors.length > 0
          ) {
            // Extraire les messages d'erreur spécifiques
            const errorMessages = error.error.errors.map(
              (err: any) => err.defaultMessage || err.message
            );
            this.errorMessage = errorMessages.join(' • ');
          } else if (error.error && error.error.message) {
            this.errorMessage = error.error.message;
          } else {
            this.errorMessage = 'Inscription échouée. Veuillez réessayer.';
          }
        }
      },
    });
  }

  goHome() {
    this.router.navigate(['/']);
  }

  private strictEmailValidator(
    control: AbstractControl
  ): ValidationErrors | null {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (control.value && !emailRegex.test(control.value)) {
      return { strictEmail: true };
    }
    return null;
  }

  private strongPasswordValidator(
    control: AbstractControl
  ): ValidationErrors | null {
    const password = control.value;
    if (!password) return null;

    const regex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).*$/;
    if (!regex.test(password)) {
      return { strongPassword: true };
    }
    return null;
  }
}
