import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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
    email: ['', [Validators.required, Validators.email]],
    username: [
      '',
      [Validators.required, Validators.minLength(3), Validators.maxLength(20)],
    ],
    password: [
      '',
      [Validators.required, Validators.minLength(3), Validators.maxLength(40)],
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
      error: () =>
        (this.errorMessage = 'Inscription échouée. Veuillez réessayer.'),
    });
  }

  goHome() {
    this.router.navigate(['/']);
  }
}
