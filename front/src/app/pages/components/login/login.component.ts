import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';
import { AuthResponse, LoginRequest } from 'src/app/shared/models/auth.models';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  errorMessage: string = '';

  public form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.min(3)]],
  });

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {}

  public submit(): void {
    if (this.form.invalid) {
      this.errorMessage = 'Veuillez remplir tous les champs correctement.';
      return;
    }

    const loginRequest: LoginRequest = this.form.getRawValue();
    console.log('Tentative de login avec:', loginRequest); // Debug

    this.authService.login(loginRequest).subscribe({
      next: (response: AuthResponse) => {
        console.log('Login réussi, redirection...'); // Debug
        this.router.navigate(['/topics']); // Le token est déjà stocké dans le service
      },
      error: (error) => {
        console.error('Erreur login:', error); // Debug
        this.errorMessage = 'Email or password is incorrect';
      },
    });
  }

  goHome() {
    this.router.navigate(['/']);
  }
}
