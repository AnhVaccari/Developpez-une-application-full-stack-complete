import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/services/auth.service';
import { AuthResponse, LoginRequest } from 'src/app/shared/models/auth.model';

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

    this.authService.login(loginRequest).subscribe({
      next: () => {
        this.router.navigate(['/topics']);
      },
      error: () => {
        this.errorMessage = 'Email or password is incorrect';
      },
    });
  }

  goHome() {
    this.router.navigate(['/']);
  }
}
